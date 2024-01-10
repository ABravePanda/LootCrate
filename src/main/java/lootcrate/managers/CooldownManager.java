package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.FileType;
import lootcrate.objects.Cooldown;
import lootcrate.objects.Crate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.*;

public class CooldownManager extends FileManager{

    private final String PREFIX = "cooldowns";
    private File cooldownFile;
    private List<Cooldown> cooldowns;

    public CooldownManager(LootCrate plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        this.cooldowns = new ArrayList<>();
        loadFile();
    }

    @Override
    public void disable() {
        saveFile();
    }

    private void loadFile() {
        cooldownFile = createFile(FileType.COOLDOWNS);
        FileConfiguration configuration = getConfiguration(cooldownFile);

        if (configuration.contains(PREFIX)) {
            List<Map<String, Object>> serializedCooldowns = (List<Map<String, Object>>) configuration.getList(PREFIX);


            for (Map<String, Object> serializedCooldown : serializedCooldowns) {
                Cooldown cooldown = new Cooldown(serializedCooldown);
                cooldowns.add(cooldown);
            }
        }
    }

    private void saveFile() {
        FileConfiguration configuration = getConfiguration(cooldownFile);

        List<Map<String, Object>> serializedCooldowns = new ArrayList<>();
        for (Cooldown cooldown : cooldowns) {
            serializedCooldowns.add(cooldown.serialize());
        }

        configuration.set(PREFIX, serializedCooldowns);
        saveFile(cooldownFile, configuration);
    }



    public void addCooldown(UUID playerUUID, Crate crate) {
        int cooldownTime = (int) crate.getOption(CrateOptionType.COOLDOWN).getValue();
        if(cooldownTime == 0) return;
        Cooldown cooldown = new Cooldown(playerUUID, crate.getId(), System.currentTimeMillis(), cooldownTime);
        cooldowns.add(cooldown);
    }

    public boolean canOpen(UUID playerUUID, Crate crate) {
        Cooldown cooldown = getCooldown(playerUUID, crate);
        if(cooldown == null) return true;
        if(cooldown.isOver()) {
            cooldowns.remove(cooldown);
            return true;
        }
        return false;
    }

    public double timeLeft(UUID playerUUID, Crate crate) {
        Cooldown cooldown = getCooldown(playerUUID, crate);
        if(cooldown == null) return 0D;
        return cooldown.getTimeLeft();
    }

    public Cooldown getCooldown(UUID playerUUID, Crate crate) {
        for(Cooldown cooldown : cooldowns) {
            if(cooldown.getUuid().equals(playerUUID) && crate.getId() == cooldown.getCrateId()) return cooldown;
        }
        return null;
    }

    public List<Cooldown> getCooldownsForPlayer(UUID uuid) {
        List<Cooldown> cooldownList = new ArrayList<>();
        for(Cooldown cooldown : cooldowns) {
            if(cooldown.getUuid().equals(uuid)) cooldownList.add(cooldown);
        }
        return cooldownList;
    }

}
