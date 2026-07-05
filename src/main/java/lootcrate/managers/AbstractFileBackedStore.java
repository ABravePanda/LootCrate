package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.FileType;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public abstract class AbstractFileBackedStore extends BasicManager {

    protected final FileManager fileManager;
    protected File file;
    protected FileConfiguration config;

    protected AbstractFileBackedStore(LootCrate plugin, FileManager fileManager) {
        super(plugin);
        this.fileManager = fileManager;
    }

    protected void loadConfig(FileType type) {
        file = fileManager.getFile(type);
        config = fileManager.getConfiguration(file);
    }

    protected void reloadConfig() {
        config = fileManager.getConfiguration(file);
    }

    protected void saveConfig() {
        fileManager.saveFile(file, config);
    }
}
