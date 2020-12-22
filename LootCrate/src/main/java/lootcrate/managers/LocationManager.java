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
    public LocationManager(LootCrate plugin)
    {
	this.plugin = plugin;
	f = new File(plugin.getDataFolder(), File.separator + "locations.yml");
	config = YamlConfiguration.loadConfiguration(f);
    }
    
   
    
    public void addCrateLocation(Location l, Crate crate)
    {
	locationList.put(l, crate);
	UUID randomUUID = UUID.randomUUID();
	config.set(randomUUID + ".Crate", crate.getId());
	config.set(randomUUID + ".Location", l.serialize());
	try
	{
	    config.save(f);
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
    
    public void removeCrateLocation(Location l)
    {
	config = YamlConfiguration.loadConfiguration(f);
	String uuid = findUUIDByLocation(l);
	if(uuid == null) return;
	config.set(uuid, null);
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
    
    public String findUUIDByLocation(Location l)
    {
	for(String s : config.getKeys(false))
	{
	    MemorySection section = (MemorySection) config.get(s);
	    Location loc = new Location(Bukkit.getWorld((String) section.get("Location.world")), (double) section.get("Location.x"), (double) section.get("Location.y"), (double) section.get("Location.z"));
	    if(l.equals(loc)) return s;
	}
	return null;
    }
    
    public void populateLocations()
    {
	for(String s : config.getKeys(false))
	{
	    MemorySection section = (MemorySection) config.get(s);
	    Location loc = new Location(Bukkit.getWorld((String) section.get("Location.world")), (double) section.get("Location.x"), (double) section.get("Location.y"), (double) section.get("Location.z"));
	    Crate crate = plugin.crateManager.getCrateById(section.getInt("Crate"));
	    if(crate == null || loc == null) continue;
	    locationList.put(loc, crate);
	}
    }
    
    public Map<Location, Crate> getLocationList()
    {
	return locationList;
    }
}
