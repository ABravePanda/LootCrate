package com.tompkins_development.spigot.lootcrate.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.obj.Crate;

public class CrateCacheManager
{
    private LootCrate plugin;
    private ManagerManager manager;
    private FileManager fileManager;
    private final String PATH = "lootcrates.yml";
    private List<Crate> cache;

    public CrateCacheManager(LootCrate plugin)
    {
	this.plugin = plugin;
	this.manager = plugin.getManager();
	this.fileManager = manager.getFileManager();
    }

    /**
     * Loads all crates in path to a list
     * @return A list of crates or empty list
     */
    public List<Crate> loadCache()
    {
	Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable()
	{
	    @Override
	    public void run()
	    {
		FileConfiguration config = fileManager.getConfig(PATH);
		for(String key : config.getKeys(false))
		{
		    System.out.println(key);
		}
	    }
	});
	return new ArrayList<Crate>();
    }

    /**
     * Saves the cache to the specified file
     * @return If save was successful
     */
    public boolean saveCache()
    {
	FileConfiguration config = fileManager.getConfig(PATH);
	for(Crate crate : cache)
	    config.set(crate.getUuid().toString(), crate);
	return fileManager.saveFile(PATH, config);
    }
    
    /**
     * Adds a crate to the cache list, overrides any existing crates with same uuid
     * @param crate Crate to add
     */
    public void addCrate(Crate crate)
    {
	if(containsId(crate.getUuid()))
	    removeCrate(crate);
	cache.add(crate);
    }
    
    /**
     * Returns true if the cache contains a crate with the given uuid
     * @param uuid UUID to compare
     * @return True if cache contains the uuid
     */
    public boolean containsId(UUID uuid)
    {
	for(Crate c : cache)
	    if(c.getUuid().equals(uuid)) return true;
	return false;
    }
    
    /**
     * Removes the specified crate from the cache
     * @param crate Crate to return
     */
    public void removeCrate(Crate crate)
    {
	List<Crate> crateCopy = new ArrayList<Crate>(cache);
	for(Crate c : crateCopy)
	    if(c.getUuid().equals(crate.getUuid())) cache.remove(c);
    }
}
