package com.tompkins_development.spigot.lootcrate.managers;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.enums.AnimationStyle;
import com.tompkins_development.spigot.lootcrate.enums.CrateOptionType;
import com.tompkins_development.spigot.lootcrate.enums.Option;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.objects.CrateItem;
import com.tompkins_development.spigot.lootcrate.objects.RandomCollection;

public class CrateManager
{
    private LootCrate plugin;

    /**
     * Constructor for CrateManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public CrateManager(LootCrate plugin)
    {
	this.plugin = plugin;
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
     * @see CrateItem#getMaxAmount()
     * @see CrateItem#getMinAmount()
     */
    public int getRandomAmount(CrateItem item)
    {
	if (item.getMaxAmount() < item.getMinAmount())
	    return 1;
	return ThreadLocalRandom.current().nextInt(item.getMinAmount(), item.getMaxAmount() + 1);
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
	    p.sendMessage(plugin.getMessageManager().getPrefix()
		    + ChatColor.translateAlternateColorCodes('&', crate.getOption(CrateOptionType.OPEN_MESSAGE)
			    .getValue().toString().replace("{crate_name}", crate.getName())));
	}

    }

    public void giveReward(CrateItem crateItem, Player p)
    {
	int rnd = plugin.getCrateManager().getRandomAmount(crateItem);

	if (!crateItem.isDisplay())
	{
	    for (int i = 0; i < rnd; i++)
		p.getInventory().addItem(crateItem.getItem());
	}

	int i = 1;

	for (String cmd : crateItem.getCommands())
	{
	    if (plugin.getOptionManager().valueOf(Option.DISPATCH_COMMAND_ITEM_AMOUNT))
		i = rnd;
	    for (int j = 0; j < i; j++)
		Bukkit.dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", p.getName()));
	}
    }

    public void addDefaultOptions(Crate crate)
    {

	String[] lines =
	{ "{crate_name}", "&8Right-Click&7 to Unlock", "&8Left-Click&7 to View" };

	crate.addOption(CrateOptionType.KNOCK_BACK, 1.0D);
	crate.addOption(CrateOptionType.ANIMATION_STYLE, AnimationStyle.RANDOM_GLASS.toString());
	crate.addOption(CrateOptionType.DISPLAY_CHANCES, true);
	crate.addOption(CrateOptionType.OPEN_SOUND, Sound.UI_TOAST_CHALLENGE_COMPLETE.toString());
	crate.addOption(CrateOptionType.OPEN_MESSAGE, "&fYou have opened &e{crate_name}&f.");
	crate.addOption(CrateOptionType.HOLOGRAM_LINES, Arrays.asList(lines));
	crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_X, 0.5D);
	crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Y, 1.8D);
	crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Z, 0.5D);
    }
}
