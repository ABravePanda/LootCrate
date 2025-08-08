package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gestionnaire anti-spam pour les broadcasts d'objets gagnés
 * Évite le spam de messages quand les joueurs gagnent trop d'objets rapidement
 */
public class AntiSpamBroadcastManager extends BasicManager {
    
    // Structure : Player UUID -> Crate ID -> AntiSpamData
    private final Map<UUID, Map<String, AntiSpamData>> playerAntiSpamData;
    private BukkitRunnable cleanupTask;
    
    public AntiSpamBroadcastManager(LootCrate plugin) {
        super(plugin);
        this.playerAntiSpamData = new ConcurrentHashMap<>();
    }
    
    @Override
    public void enable() {
        // Démarrer une tâche de nettoyage périodique
        cleanupTask = new BukkitRunnable() {
            @Override
            public void run() {
                cleanupOldData();
            }
        };
        cleanupTask.runTaskTimerAsynchronously(getPlugin(), 6000L, 6000L); // Toutes les 5 minutes
    }
    
    @Override
    public void disable() {
        if (cleanupTask != null) {
            cleanupTask.cancel();
        }
        playerAntiSpamData.clear();
    }
    
    /**
     * Vérifie si un broadcast doit être envoyé ou bloqué par l'anti-spam
     * @param player Le joueur qui a gagné l'objet
     * @param crate La caisse d'où vient l'objet
     * @param itemName Le nom de l'objet gagné
     * @return true si le broadcast doit être envoyé, false si bloqué par anti-spam
     */
    public boolean shouldBroadcast(Player player, Crate crate, String itemName) {
        if (!getPlugin().getConfig().getBoolean("options.broadcast-item-win-enabled", false)) {
            return false;
        }
        
        UUID playerId = player.getUniqueId();
        String crateId = crate.getName(); // Utiliser le nom de la caisse, pas l'ID
        
        // Obtenir la configuration anti-spam pour cette caisse
        AntiSpamConfig config = getAntiSpamConfig(crateId);
        
        // Si pas de configuration ou anti-spam désactivé, autoriser le broadcast
        if (config == null) {
            return true;
        }
        
        // Récupérer ou créer les données anti-spam pour ce joueur
        Map<String, AntiSpamData> playerData = playerAntiSpamData.computeIfAbsent(playerId, k -> new HashMap<>());
        
        // Récupérer ou créer les données anti-spam pour cette caisse
        AntiSpamData data = playerData.computeIfAbsent(crateId, k -> new AntiSpamData());
        
        long currentTime = System.currentTimeMillis();
        
        // Si l'anti-spam est actif, vérifier s'il doit être prolongé
        if (data.isAntiSpamActive(currentTime)) {
            // Prolonger l'anti-spam de la durée configurée
            data.extendAntiSpam(currentTime, config.antiSpamTime * 1000);
            return false; // Bloquer le broadcast
        }
        
        // Nettoyer les anciens événements
        data.cleanOldEvents(currentTime, config.timeTarget * 1000);
        
        // Ajouter l'événement actuel
        data.addEvent(currentTime);
        
        // Vérifier si on dépasse le seuil
        if (data.getEventCount() >= config.itemTarget) {
            // Activer l'anti-spam
            data.activateAntiSpam(currentTime, config.antiSpamTime * 1000);
            
            // Envoyer le message d'anti-spam
            String antiSpamMessage = config.antiSpamBroadcast
                .replace("{crate_name}", crate.getName())
                .replace("{player_name}", player.getName());
            
            Bukkit.broadcastMessage(antiSpamMessage);

            return false; // Bloquer le broadcast original
        }
        
        return true; // Autoriser le broadcast
    }
    
    /**
     * Récupère la configuration anti-spam pour une caisse donnée
     * Retourne null si la caisse n'est pas configurée ou si enable=false
     */
    private AntiSpamConfig getAntiSpamConfig(String crateId) {
        ConfigurationSection antiSpamSection = getPlugin().getConfig().getConfigurationSection("options.antispam-broadcast-item-win");
        
        if (antiSpamSection == null) {
            return null; // Pas de configuration anti-spam
        }
        
        // Chercher la configuration spécifique à la caisse
        ConfigurationSection crateSection = antiSpamSection.getConfigurationSection(crateId);
        if (crateSection == null) {
            return null; // Pas de configuration pour cette caisse
        }
        
        // Vérifier si l'anti-spam est activé pour cette caisse
        if (!crateSection.getBoolean("enable", false)) {
            return null; // Anti-spam désactivé pour cette caisse
        }
        
        return new AntiSpamConfig(
            crateSection.getInt("time-target", 2),
            crateSection.getInt("item-target", 4),
            crateSection.getString("antispam-broadcast", "{crate_name} ► &cAnti-spam activé"),
            crateSection.getInt("anti-spam-time", 3)
        );
    }
    
    /**
     * Nettoie les données anciennes pour éviter les fuites mémoire
     */
    public void cleanupOldData() {
        long currentTime = System.currentTimeMillis();
        long maxAge = 300000; // 5 minutes
        
        playerAntiSpamData.entrySet().removeIf(playerEntry -> {
            playerEntry.getValue().entrySet().removeIf(crateEntry -> {
                AntiSpamData data = crateEntry.getValue();
                data.cleanOldEvents(currentTime, maxAge);
                return data.isEmpty() && !data.isAntiSpamActive(currentTime);
            });
            return playerEntry.getValue().isEmpty();
        });
    }
    
    /**
     * Classe pour stocker les données anti-spam d'un joueur pour une caisse
     */
    static class AntiSpamData {
        private long antiSpamEndTime = 0;
        private final Map<Long, Integer> events = new HashMap<>(); // timestamp -> count
        
        public void addEvent(long timestamp) {
            long timeSlot = timestamp / 1000; // Regrouper par seconde
            events.merge(timeSlot, 1, Integer::sum);
        }
        
        public void cleanOldEvents(long currentTime, long maxAge) {
            long cutoffTime = (currentTime - maxAge) / 1000;
            events.entrySet().removeIf(entry -> entry.getKey() < cutoffTime);
        }
        
        public int getEventCount() {
            return events.values().stream().mapToInt(Integer::intValue).sum();
        }
        
        public void activateAntiSpam(long currentTime, long duration) {
            this.antiSpamEndTime = currentTime + duration;
        }
        
        public void extendAntiSpam(long currentTime, long duration) {
            this.antiSpamEndTime = currentTime + duration;
        }
        
        public boolean isAntiSpamActive(long currentTime) {
            return currentTime < antiSpamEndTime;
        }
        
        public boolean isEmpty() {
            return events.isEmpty();
        }
    }
    
    /**
     * Configuration anti-spam pour une caisse
     */
    static class AntiSpamConfig {
        final int timeTarget;
        final int itemTarget;
        final String antiSpamBroadcast;
        final int antiSpamTime;
        
        public AntiSpamConfig(int timeTarget, int itemTarget, String antiSpamBroadcast, int antiSpamTime) {
            this.timeTarget = timeTarget;
            this.itemTarget = itemTarget;
            this.antiSpamBroadcast = antiSpamBroadcast;
            this.antiSpamTime = antiSpamTime;
        }
    }
}
