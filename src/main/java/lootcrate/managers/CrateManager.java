package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.*;
import lootcrate.managers.LocationManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.RandomCollection;
import lootcrate.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Particle;

public class CrateManager extends BasicManager {

    /**
     * Constructor for CrateManager
     *
     * @param plugin An instance of the plugin
     */
    public CrateManager(LootCrate plugin) {
        super(plugin);
    }

    /**
     * Retrieves a crate item within the crate with the id
     *
     * @param crate The crate to be searched
     * @param id    The id to be compared with
     * @return The crate with the same id, or null
     */
    public CrateItem getCrateItemById(Crate crate, int id) {
        for (CrateItem item : crate.getItems()) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    /**
     * Retrieves a random CrateItem from the specified crate
     *
     * @param crate The crate wishing to be searched
     * @return A random CrateItem from the crate
     */
    public CrateItem getRandomItem(Crate crate) {
        RandomCollection<String> items = new RandomCollection<String>();

        for (CrateItem item : new ArrayList<>(crate.getItems()))
            items.add(item.getChance(), item);
        return items.next();
    }

    /**
     * Gets a random amount from a crate item
     *
     * @param item The CrateItem whos amount you want
     * @return A random amount between getMinAmount() and getMaxAmount()
     * @see CrateItem#getMaxAmount()
     * @see CrateItem#getMinAmount()
     */
    public int getRandomAmount(CrateItem item) {
        if (item.getMaxAmount() < item.getMinAmount())
            return 1;
        if(item.getMaxAmount() == item.getMinAmount())
            return item.getMinAmount();
        return ThreadLocalRandom.current().nextInt(item.getMinAmount(), item.getMaxAmount() + 1);
    }

    /**
     * Plays crate sound and sends message to player to open crate for
     *
     * @param crate Crate to open
     * @param p Player to open crate for
     */
    public void crateOpenEffects(Crate crate, Player p) {
        // Effets visuels spéciaux à l'ouverture
        org.bukkit.configuration.ConfigurationSection effectSection = this.getPlugin().getConfig().getConfigurationSection("effect");
        if (effectSection != null && effectSection.contains(crate.getName())) {
            String effect = effectSection.getString(crate.getName());
            if (effect != null) {
                // Pour chaque position de la caisse
                List<org.bukkit.Location> locations = this.getPlugin().getManager(LocationManager.class).getCrateLocations(crate);
                for (org.bukkit.Location loc : locations) {
                    org.bukkit.Location effectLoc = loc.clone().add(0.5, 1, 0.5);
                    // Effets visuels avancés
                    if (effect.equalsIgnoreCase("explosion")) {
                        effectLoc.getWorld().spawnParticle(Particle.EXPLOSION, effectLoc, 1, 0, 0, 0, 0);
                    } else if (effect.equalsIgnoreCase("thunder") || effect.equalsIgnoreCase("lightning")) {
                        // Effet d'éclair avec particules et son
                        effectLoc.getWorld().spawnParticle(Particle.EXPLOSION, effectLoc, 3, 0.5, 0.5, 0.5, 0.1);
                        effectLoc.getWorld().playSound(effectLoc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
                        effectLoc.getWorld().playSound(effectLoc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0f, 1.0f);
                    } else if (effect.equalsIgnoreCase("enchant")) {
                        effectLoc.getWorld().spawnParticle(Particle.ENCHANT, effectLoc, 50, 1, 1, 1, 0.5);
                    } else if (effect.equalsIgnoreCase("flame")) {
                        effectLoc.getWorld().spawnParticle(Particle.FLAME, effectLoc, 30, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("heart")) {
                        effectLoc.getWorld().spawnParticle(Particle.HEART, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("totem")) {
                        effectLoc.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("note")) {
                        effectLoc.getWorld().spawnParticle(Particle.NOTE, effectLoc, 15, 0.5, 0.5, 0.5, 1);
                    } else if (effect.equalsIgnoreCase("smoke")) {
                        effectLoc.getWorld().spawnParticle(Particle.SMOKE, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("crit")) {
                        effectLoc.getWorld().spawnParticle(Particle.CRIT, effectLoc, 25, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("happy_villager")) {
                        effectLoc.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, effectLoc, 15, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("angry_villager")) {
                        effectLoc.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("cloud")) {
                        effectLoc.getWorld().spawnParticle(Particle.CLOUD, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("lava")) {
                        effectLoc.getWorld().spawnParticle(Particle.LAVA, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("drip_lava")) {
                        effectLoc.getWorld().spawnParticle(Particle.DRIPPING_LAVA, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("drip_water")) {
                        effectLoc.getWorld().spawnParticle(Particle.DRIPPING_WATER, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("portal")) {
                        effectLoc.getWorld().spawnParticle(Particle.PORTAL, effectLoc, 40, 1, 1, 1, 0.2);
                    } else if (effect.equalsIgnoreCase("dragon_breath")) {
                        effectLoc.getWorld().spawnParticle(Particle.DRAGON_BREATH, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("spit")) {
                        effectLoc.getWorld().spawnParticle(Particle.SPIT, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("sneeze")) {
                        effectLoc.getWorld().spawnParticle(Particle.SNEEZE, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("firework")) {
                        effectLoc.getWorld().spawnParticle(Particle.FIREWORK, effectLoc, 30, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("ash")) {
                        effectLoc.getWorld().spawnParticle(Particle.ASH, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("soul")) {
                        effectLoc.getWorld().spawnParticle(Particle.SOUL, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("soul_fire")) {
                        effectLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("snowflake")) {
                        effectLoc.getWorld().spawnParticle(Particle.SNOWFLAKE, effectLoc, 20, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("slime")) {
                        effectLoc.getWorld().spawnParticle(Particle.ITEM_SLIME, effectLoc, 10, 0.5, 0.5, 0.5, 0.01);
                    } else if (effect.equalsIgnoreCase("bubble")) {
                        effectLoc.getWorld().spawnParticle(Particle.BUBBLE, effectLoc, 15, 0.5, 0.5, 0.5, 0.01);
                    }
                }
            }
        }
        // play sound
        if (crate.getOption(CrateOptionType.OPEN_SOUND).getValue() != null) {
            if (crate.getOption(CrateOptionType.OPEN_SOUND).getValue().toString().equalsIgnoreCase("none"))
                return;
            if (SoundUtils.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue()) == null)
                return;
            int soundVolume = 1;
            if (crate.getOption(CrateOptionType.SOUND_VOLUME) != null)
                soundVolume = (int) crate.getOption(CrateOptionType.SOUND_VOLUME).getValue();
            Sounds sound = SoundUtils.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue());
            SoundUtils.playSound(p, sound, soundVolume, 1);
        }

        // get message, send it
        if (crate.getOption(CrateOptionType.OPEN_MESSAGE).getValue() != null) {
            if (crate.getOption(CrateOptionType.OPEN_MESSAGE).getValue().toString().equalsIgnoreCase("none"))
                return;
            p.sendMessage(this.getPlugin().getManager(MessageManager.class).getPrefix()
                    + ChatColor.translateAlternateColorCodes('&', crate.getOption(CrateOptionType.OPEN_MESSAGE)
                    .getValue().toString().replace("{crate_name}", crate.getName())));
        }

    }

    public void giveReward(CrateItem crateItem, Player p, String crateName, Crate crate) {
        int rnd = this.getPlugin().getManager(CrateManager.class).getRandomAmount(crateItem);

        if (!crateItem.isDisplay()) {
            for (int i = 0; i < rnd; i++)
                p.getInventory().addItem(crateItem.getItem());
            
            handleItemWinBroadcast(crateItem, p, crateName, crate);
        }

        executeItemCommands(crateItem, p, crateName, rnd);
    }

    private void handleItemWinBroadcast(CrateItem crateItem, Player p, String crateName, Crate crate) {
        boolean broadcastEnabled = false;
        Object broadcastOption = this.getPlugin().getManager(OptionManager.class).valueOf(Option.BROADCAST_ITEM_WIN_ENABLED);
        if (broadcastOption instanceof Boolean) {
            broadcastEnabled = (Boolean) broadcastOption;
        }
        
        if (broadcastEnabled) {
            String itemName = lootcrate.utils.ItemUtils.getDisplayOrTranslatedName(this.getPlugin(), crateItem.getItem());
            
            // Vérifier l'anti-spam avant d'envoyer le broadcast
            AntiSpamBroadcastManager antiSpamManager = this.getPlugin().getManager(AntiSpamBroadcastManager.class);
            if (antiSpamManager != null && antiSpamManager.shouldBroadcast(p, crate, itemName)) {
                String message = (String) this.getPlugin().getManager(OptionManager.class).valueOf(Option.BROADCAST_ITEM_WIN_MESSAGE);
                message = message.replace("{crate_name}", crateName)
                                 .replace("{player_name}", p.getName())
                                 .replace("{item_name}", itemName);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }

    private void executeItemCommands(CrateItem crateItem, Player p, String crateName, int rnd) {
        int i = 1;

        for (String cmd : crateItem.getCommands()) {
            Object dispatchOption = this.getPlugin().getManager(OptionManager.class).valueOf(Option.DISPATCH_COMMAND_ITEM_AMOUNT);
            if (dispatchOption instanceof Boolean && (Boolean) dispatchOption)
                i = rnd;
            for (int j = 0; j < i; j++)
                Bukkit.dispatchCommand(this.getPlugin().getServer().getConsoleSender(), cmd
                        .replace("{player}", p.getName())
                        .replace("{amount}", rnd + "")
                        .replace("{reward}", (crateItem.getItem().getItemMeta() != null && crateItem.getItem().getItemMeta().hasDisplayName())
                                ? crateItem.getItem().getItemMeta().getDisplayName()
                                : crateItem.getItem().getType().toString()
                        )
                        .replace("{crate_name}", crateName)
                );
        }
    }

    public void addDefaultOptions(Crate crate) {

        String[] lines =
                {"{crate_name}", "&8Right-Click&7 to Unlock", "&8Left-Click&7 to View"};

        crate.addOption(CrateOptionType.KNOCK_BACK, 1.0D);
        crate.addOption(CrateOptionType.COOLDOWN, 0);
        crate.addOption(CrateOptionType.ANIMATION_STYLE, AnimationStyle.RANDOM_GLASS.toString());
        crate.addOption(CrateOptionType.SORT_TYPE, SortType.CHANCE.toString());
        crate.addOption(CrateOptionType.DISPLAY_CHANCES, true);
        crate.addOption(CrateOptionType.OPEN_SOUND, "ui.toast.challenge_complete");
        crate.addOption(CrateOptionType.OPEN_MESSAGE, "&fYou have opened &e{crate_name}&f.");
        crate.addOption(CrateOptionType.HOLOGRAM_ENABLED, true);
        crate.addOption(CrateOptionType.HOLOGRAM_LINES, Arrays.asList(lines));
        crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_X, 0.5D);
        crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Y, 1.8D);
        crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Z, 0.5D);
    }

    public Crate getCrateFromItemID(int id)
    {
        List<Crate> crates = this.getPlugin().getManager(CacheManager.class).getCache();
        for(Crate crate : crates)
            for(CrateItem item : crate.getItems())
                if(item.getId() == id) return crate;
        return null;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
