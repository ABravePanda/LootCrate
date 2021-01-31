package lootcrate.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;

public class CacheManager
{
    private LootCrate plugin;
    private List<Crate> cache;

    public CacheManager(LootCrate plugin)
    {
	cache = new ArrayList<Crate>();
	this.plugin = plugin;
    }

    /**
     * Updates Crate into cache
     * 
     * @param Crate
     *            Crate to update
     */
    public void update(Crate Crate)
    {
	plugin.fileManager.saveCrate(Crate);
	;
	if (cache.contains(Crate))
	    cache.remove(Crate);
	cache.add(Crate);
    }

    public void rename(String oldCrate, Crate Crate)
    {
	plugin.fileManager.overrideSave(oldCrate, Crate);
	if (cache.contains(oldCrate))
	    cache.remove(oldCrate);
	cache.add(Crate);
    }

    /**
     * Removes Crate from the file and cache
     * 
     * @param Crate
     *            Crate to remove
     */
    public void remove(Crate Crate)
    {
	plugin.fileManager.removeCrate(Crate);
	List<Crate> copiedCache = new ArrayList<Crate>(cache);

	for (Crate cacheCrate : copiedCache)
	{
	    if (cacheCrate.getId() == Crate.getId())
		cache.remove(cacheCrate);
	}
    }

    /**
     * Gets Crate by given name in cache
     * 
     * @param name
     *            Name to look for
     * @return Crate or null
     */
    public Crate getCrateById(int id)
    {
	for (Crate crate : cache)
	{
	    if (crate.getId() == id)
		return crate;
	}
	return null;
    }

    /**
     * Gets the cache list
     * 
     * @return cache list
     */
    public List<Crate> getCache()
    {
	return cache;
    }

    /**
     * Loads the cache asynchronously
     */
    public void load()
    {
	Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable()
	{
	    @Override
	    public void run()
	    {
		cache = plugin.fileManager.loadAllCrates();
	    }
	});
    }

    /**
     * Wipes the Crates file, then saves the full cache into Crates file
     */
    public void save()
    {
	File f = new File(plugin.getDataFolder(), File.separator + "crates.yml");
	YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

	for (String key : config.getKeys(false))
	{
	    config.set(key, null);
	}

	try
	{
	    config.save(f);
	} catch (IOException e)
	{
	    e.printStackTrace();
	}

	for (Crate Crate : cache)
	{
	    plugin.fileManager.saveCrate(Crate);
	}
    }

    /**
     * Reloads the cache
     */
    public void reload()
    {
	load();
    }
}
