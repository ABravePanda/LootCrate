package lootcrate.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.RandomCollection;

public class CrateManager
{
    private LootCrate plugin;
    private final String PREFIX = "crates.";
    File f;
    FileConfiguration config;

    public CrateManager(LootCrate plugin)
    {
	this.plugin = plugin;
	f = new File(plugin.getDataFolder(), File.separator + "crates.yml");
	config = YamlConfiguration.loadConfiguration(f);
    }

    public void save(Crate crate)
    {
	config.set(PREFIX + crate.getId() + "", crate.serialize());

	saveFile();
    }

    public List<Crate> load()
    {
	config = YamlConfiguration.loadConfiguration(f);
	List<Crate> crates = new ArrayList<Crate>();
	HashMap<String, Object> map = new HashMap<String, Object>();
	for (String s : config.getConfigurationSection(PREFIX).getKeys(false))
	{
	    map.put(s, config.get(PREFIX + s));
	}
	for (String s : map.keySet())
	{
	    Crate crate = Crate.deserialize(PREFIX + s, config);
	    crates.add(crate);
	}
	return crates;
    }

    public Crate getCrateById(int id)
    {
	for (Crate crate : load())
	{
	    if (crate.getId() == id)
		return crate;
	}
	return null;
    }
    
    public CrateItem getCrateItemById(Crate crate, int id)
    {
	for (CrateItem item : crate.getItems())
	{
	    if (item.getId() == id)
		return item;
	}
	return null;
    }

    public CrateItem getRandomItem(Crate crate)
    {
	RandomCollection<String> items = new RandomCollection<String>();
	
	for(CrateItem item : crate.getItems())
	    items.add(item.getChance(), item);
	return items.next();
    }
    
    public int getRandomAmount(CrateItem item)
    {
	if(item.getMaxAmount() < item.getMinAmount()) return 1;
	return ThreadLocalRandom.current().nextInt(item.getMinAmount(), item.getMaxAmount() + 1);
    }

    private void saveFile()
    {
	try
	{
	    config.save(f);
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
