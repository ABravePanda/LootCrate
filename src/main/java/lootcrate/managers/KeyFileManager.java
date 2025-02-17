package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.FileType;
import lootcrate.objects.Crate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class KeyFileManager extends FileManager {
    private final String KEY_PREFIX = "keys.";
    public File keyFile;

    public KeyFileManager(LootCrate plugin) {
        super(plugin);
    }


    public Map<UUID, List<Integer>> loadCache()
    {
        FileConfiguration config = this.getPlugin().getManager(FileManager.class).getConfiguration(keyFile);
        Map<UUID, List<Integer>> map = new HashMap();

        if(config.getConfigurationSection(KEY_PREFIX) == null) return new HashMap<>();

        for (String s : config.getConfigurationSection(KEY_PREFIX).getKeys(false)) {
            List<Integer> integerList = config.getIntegerList(KEY_PREFIX + s);
            map.put(UUID.fromString(s), integerList);
        }

        return map;
    }

    /**
     * Saves the key cache to file
     *
     * @param keyMap the map of keys to be saved
     */
    public void saveCache(Map<UUID, List<Integer>> keyMap) {
        FileConfiguration config = this.getPlugin().getManager(FileManager.class).getConfiguration(keyFile);

        for(UUID uuid : keyMap.keySet()) {
            config.set(KEY_PREFIX + uuid, keyMap.get(uuid));
        }
        getPlugin().getManager(FileManager.class).saveFile(keyFile, config);
    }


    /**
     * Loads the files into the variables
     */
    private void loadFiles() {
        keyFile = this.getPlugin().getManager(FileManager.class).getFile(FileType.KEYS);
    }

    @Override
    public void enable() {
        loadFiles();
    }

    @Override
    public void disable() {

    }

    public void reload() {
        loadFiles();
    }
}
