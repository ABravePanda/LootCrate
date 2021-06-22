package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.FileType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager implements Manager {

    private final LootCrate plugin;

    public FileManager(LootCrate plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public File createFile(FileType type)
    {
        File file = new File(plugin.getDataFolder(), File.separator + type.getName());
        return file;
    }

    public YamlConfiguration getConfiguration(File file)
    {
        return YamlConfiguration.loadConfiguration(file);
    }

    public File getFile(FileType type)
    {
        return createFile(type);
    }

    public void saveFile(File file, FileConfiguration fileConfiguration)
    {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
