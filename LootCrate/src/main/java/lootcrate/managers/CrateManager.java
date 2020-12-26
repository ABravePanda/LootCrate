package lootcrate.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.RandomCollection;
import lootcrate.other.CrateOptionType;

public class CrateManager
{
    private LootCrate plugin;
    private final String PREFIX = "crates.";
    File f;
    FileConfiguration config;

    /**
     * Constructor for CrateManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public CrateManager(LootCrate plugin)
    {
	this.plugin = plugin;
	f = new File(plugin.getDataFolder(), File.separator + "crates.yml");
	config = YamlConfiguration.loadConfiguration(f);
    }

    /**
     * Saves a crate to the proper configuration file
     * 
     * @param crate
     *            The crate you would like to have saved
     */
    public void save(Crate crate)
    {
	config.set(PREFIX + crate.getId() + "", crate.serialize());

	saveFile();
    }

    /**
     * Reloads the config file
     */
    public void reload()
    {
	config = YamlConfiguration.loadConfiguration(f);
    }

    /**
     * Loads all the crates in the save file
     * 
     * @return A list of all crates
     */
    public List<Crate> load()
    {
	reload();
	List<Crate> crates = new ArrayList<Crate>();
	HashMap<String, Object> map = new HashMap<String, Object>();
	if (config.getConfigurationSection(PREFIX) == null)
	    return crates;
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

    /**
     * Deletes specified crate
     * 
     * @param crate
     *            Crate to be deleted
     */
    public void deleteCrate(Crate crate)
    {
	reload();
	config.set(PREFIX + crate.getId() + "", null);
	saveFile();
    }

    /**
     * Retrieves a crate with the relative id
     * 
     * @param id
     *            The id you are looking for
     * @return The crate with the same id, or null
     */
    public Crate getCrateById(int id)
    {
	for (Crate crate : load())
	{
	    if (crate.getId() == id)
		return crate;
	}
	return null;
    }

    /**
     * Retrieves a crate item within the crate with the id
     * 
     * @param crate
     *            The crate to be searched
     * @param id
     *            The id to be compared with
     * @return The crate with the same id, or null
     */
    public CrateItem getCrateItemById(Crate crate, int id)
    {
	for (CrateItem item : crate.getItems())
	{
	    if (item.getId() == id)
		return item;
	}
	return null;
    }

    /**
     * Retrieves a random CrateItem from the specified crate
     * 
     * @param crate
     *            The crate wishing to be searched
     * @return A random CrateItem from the crate
     */
    public CrateItem getRandomItem(Crate crate)
    {
	RandomCollection<String> items = new RandomCollection<String>();

	for (CrateItem item : crate.getItems())
	    items.add(item.getChance(), item);
	return items.next();
    }

    /**
     * Gets a random amount from a crate item
     * 
     * @param item
     *            The CrateItem whos amount you want
     * @return A random amount between getMinAmount() and getMaxAmount()
     * @see CrateItem.getMaxAmount()
     * @see CrateItem.getMinAmount()
     */
    public int getRandomAmount(CrateItem item)
    {
	if (item.getMaxAmount() < item.getMinAmount())
	    return 1;
	return ThreadLocalRandom.current().nextInt(item.getMinAmount(), item.getMaxAmount() + 1);
    }

    /**
     * Saves the crate file
     */
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

    public void crateOpenEffects(Crate crate, Player p)
    {
	// play sound
	if (crate.getOption(CrateOptionType.OPEN_SOUND).getValue() != null)
	{
	    if (crate.getOption(CrateOptionType.OPEN_SOUND).getValue().toString().equalsIgnoreCase("none"))
		return;
	    if (Sound.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue()) == null)
		return;
	    p.playSound(p.getLocation(), Sound.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue()),
		    1, 1);
	}

	// get message, send it
	if (crate.getOption(CrateOptionType.OPEN_MESSAGE).getValue() != null)
	{
	    if (crate.getOption(CrateOptionType.OPEN_MESSAGE).getValue().toString().equalsIgnoreCase("none"))
		return;
	    p.sendMessage(plugin.messageManager.getPrefix()
		    + ChatColor.translateAlternateColorCodes('&', crate.getOption(CrateOptionType.OPEN_MESSAGE)
			    .getValue().toString().replace("{crate_name}", crate.getName())));
	}	

    }
}
