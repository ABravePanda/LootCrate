package com.tompkins_development.spigot.lootcrate.mgr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.tompkins_development.spigot.lootcrate.LootCrate;

public class FileManager
{
    private LootCrate plugin;
    
    public FileManager(LootCrate plugin)
    {
	this.plugin = plugin;
    }
    
    /**
     * Retrieves the file configuration from file at given path
     * @param path Path at which file is located, including name and extension
     * @return FileConfiguration of file
     */
    public FileConfiguration getConfig(String path)
    {
	 return YamlConfiguration.loadConfiguration(getFile(path));
    }
    
    /**
     * Retrieves the file configuration from given file
     * @param file File to retrieve config of
     * @return FileConfiguration of file
     */
    public FileConfiguration getConfig(File file)
    {
	 return YamlConfiguration.loadConfiguration(file);
    }
    
    /**
     * Saves the file at the given path with the given configuration
     * @param path Path at which file is located, including name and extension
     * @param config The FileConfiguration to save to the file
     * @return If file was successfully saved
     */
    public boolean saveFile(String path, FileConfiguration config)
    {
	try
	{
	    config.save(getFile(path));
	    return true;
	} catch (IOException e)
	{
	    return false;
	}
    }
    
    /**
     * Saves the given file with given configuration
     * @param file File to save
     * @param config The FileConfiguration to save to the file
     * @return If file was successfully saved
     */
    public boolean saveFile(File file, FileConfiguration config)
    {
	try
	{
	    config.save(file);
	    return true;
	} catch (IOException e)
	{
	    return false;
	}
    }
    
    /**
     * Retrieves a file at given path. If nonexistant, file is made
     * @param path Path at which file is located, including name and extension
     * @return The file at specified path
     */
    public File getFile(String path)
    {
	File file = new File(plugin.getDataFolder(), File.separator + path);
	 if(!file.exists())
	     file.mkdirs();
	 return file;
    }
}
