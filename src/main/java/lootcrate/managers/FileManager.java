package lootcrate.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;

public class FileManager
{
    private LootCrate plugin;
    public File crateFile;
    private final String CRATE_PREFIX = "crates.";

    public FileManager(LootCrate plugin)
    {
	this.plugin = plugin;
	loadFiles();
    }

    /**
     * Gets a Crate object of given name from the crates file
     * 
     * @param name
     *            Name of the Crate to retrieve
     * @return The Crate object of the given name or null
     */
    public Crate getCrateFromFile(String name)
    {
	FileConfiguration config = YamlConfiguration.loadConfiguration(crateFile);
	if (config.get(CRATE_PREFIX + name) == null)
	    return null;
	if (config.get(CRATE_PREFIX + name) instanceof MemorySection)
	{
	    Map<String, Object> map = new HashMap<String, Object>();
	    MemorySection m = (MemorySection) config.get(CRATE_PREFIX + name);
	    map.put("Id", m.get("Id"));
	    map.put("Name", m.get("Name"));
	    map.put("Options", m.get("Options"));
	    map.put("Key", m.get("Key"));
	    map.put("Items", m.get("Items"));
	    return new Crate(plugin, map);
	} else
	    return new Crate(plugin, (Map<String, Object>) config.get(CRATE_PREFIX + name));
    }

    /**
     * Gets all the crates that are currently in file
     * 
     * @return A list of crates
     */
    public List<Crate> loadAllCrates()
    {
	FileConfiguration config = YamlConfiguration.loadConfiguration(crateFile);
	List<Crate> crates = new ArrayList<Crate>();
	HashMap<String, Object> map = new HashMap<String, Object>();
	if (config.getConfigurationSection(CRATE_PREFIX) == null)
	    return crates;
	for (String s : config.getConfigurationSection(CRATE_PREFIX).getKeys(false))
	{
	    if (getCrateFromFile(s) != null)
		crates.add(getCrateFromFile(s));
	    else
		continue;
	}
	return crates;
    }

    /**
     * Saves the given Crate to the "crates.yml" file
     * 
     * @param Crate
     *            The Crate to be saved
     */
    public void saveCrate(Crate crate)
    {
	FileConfiguration config = YamlConfiguration.loadConfiguration(crateFile);
	config.set(CRATE_PREFIX + crate.getId(), crate.serialize());
	saveFile(config);
    }

    /**
     * Saves the given Crate as a replacement for the given name To be used only
     * for Crate renaming
     * 
     * @param oldName
     *            Name to override
     * @param Crate
     *            Crate to save
     */
    public void overrideSave(String oldName, Crate crate)
    {
	FileConfiguration config = YamlConfiguration.loadConfiguration(crateFile);
	config.set(CRATE_PREFIX + oldName, null);
	config.set(CRATE_PREFIX + crate.getId(), crate.serialize());
	saveFile(config);
    }

    /**
     * Removes the given Crate from the "crates.yml" file
     * 
     * @param Crate
     *            The Crate to be remove
     */
    public void removeCrate(Crate crate)
    {
	FileConfiguration config = YamlConfiguration.loadConfiguration(crateFile);
	config.set(CRATE_PREFIX + crate.getId(), null);
	saveFile(config);
    }

    /**
     * Loads the files into the variables
     */
    private void loadFiles()
    {
	crateFile = new File(plugin.getDataFolder(), File.separator + "crates.yml");
    }

    /**
     * Saves the file with the given configuration
     * 
     * @param config
     *            Configuration to save to file
     */
    private void saveFile(FileConfiguration config)
    {
	try
	{
	    config.save(crateFile);
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
}
