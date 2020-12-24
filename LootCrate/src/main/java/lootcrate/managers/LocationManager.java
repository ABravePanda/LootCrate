package lootcrate.managers;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;

public class LocationManager
{

    private Map<Location, Crate> locationList = new LinkedHashMap<Location, Crate>();

    LootCrate plugin;
    String locationPrefix = "locations.";
    File f;
    FileConfiguration config;

    /**
     * Constructor for LocationManager
     * 
     * @param plugin
     *            Instance of plugin
     */
    public LocationManager(LootCrate plugin)
    {
	this.plugin = plugin;
	f = new File(plugin.getDataFolder(), File.separator + "locations.yml");
	config = YamlConfiguration.loadConfiguration(f);
    }

    /**
     * Reloads the config and repopulates location list
     */
    public void reload()
    {
	config = YamlConfiguration.loadConfiguration(f);
	populateLocations();
    }

    /**
     * Adds a crate to location list/file
     * 
     * @param l
     *            Location to be added
     * @param crate
     *            Crate to be added
     */
    public void addCrateLocation(Location l, Crate crate)
    {
	locationList.put(l, crate);
	UUID randomUUID = UUID.randomUUID();
	String uuid = findUUIDByLocation(l);
	if (uuid != null)
	    randomUUID = UUID.fromString(uuid);
	config.set(randomUUID + ".Crate", crate.getId());
	config.set(randomUUID + ".Location", l.serialize());
	try
	{
	    config.save(f);
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	reload();
    }

    /**
     * Removes crate from list/file
     * 
     * @param l
     *            Location to be removed
     */
    public void removeCrateLocation(Location l)
    {
	reload();
	config = YamlConfiguration.loadConfiguration(f);
	String uuid = findUUIDByLocation(l);
	if (uuid == null)
	    return;
	config.set(uuid, null);
	if (locationList.containsKey(l))
	    locationList.remove(l);
	try
	{
	    config.save(f);
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Returns the locations uuid as specified by the file
     * 
     * @param l
     *            Location to be searched
     * @return UUID of the location or null
     */
    public String findUUIDByLocation(Location l)
    {
	reload();
	for (String s : config.getKeys(false))
	{
	    MemorySection section = (MemorySection) config.get(s);
	    if (section.get("Location") == null)
		continue;
	    if (Bukkit.getWorld((String) section.get("Location.world")) == null)
		continue;
	    Location loc = new Location(Bukkit.getWorld((String) section.get("Location.world")),
		    (double) section.get("Location.x"), (double) section.get("Location.y"),
		    (double) section.get("Location.z"));
	    if (l.equals(loc))
		return s;
	}
	return null;
    }

    /**
     * Populates the location file
     */
    public void populateLocations()
    {
	locationList.clear();
	for (String s : config.getKeys(false))
	{
	    MemorySection section = (MemorySection) config.get(s);
	    Location loc = new Location(Bukkit.getWorld((String) section.get("Location.world")),
		    (double) section.get("Location.x"), (double) section.get("Location.y"),
		    (double) section.get("Location.z"));
	    Crate crate = plugin.crateManager.getCrateById(section.getInt("Crate"));
	    if (crate == null || loc == null)
		continue;
	    locationList.put(loc, crate);
	}
    }

    /**
     * Returns the location list
     * 
     * @return location list of all locations and crates
     */
    public Map<Location, Crate> getLocationList()
    {
	return locationList;
    }
}
