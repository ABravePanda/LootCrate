package lootcrate.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.other.CrateOptionType;
import lootcrate.utils.ObjUtils;
import net.md_5.bungee.api.ChatColor;

public class InventoryManager
{

    private LootCrate plugin;

    /**
     * Constructor for InventoryManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public InventoryManager(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    /**
     * 
     * @param crate
     * @return
     */
    public List<CrateItem> addCrateEffects(Crate crate)
    {
	List<CrateItem> items = crate.getItems();
	List<CrateItem> newList = new ArrayList<CrateItem>();
	Collections.sort(items);
	for (CrateItem item : new ArrayList<CrateItem>(items))
	{
	    item = ObjUtils.assignRandomIDToItem(plugin, item);
	    ItemStack itemStack = item.getItem().clone();
	    if ((boolean) crate.getOption(CrateOptionType.DISPLAY_CHANCES).getValue())
	    {
		ItemMeta meta = itemStack.getItemMeta();

		List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
		lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Chance: " + ChatColor.RED + item.getChance() + "%");
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		item.setItem(itemStack);
		newList.add(item);
	    }
	}
	return newList;
    }

    /**
     * Closes the inventory of specified player
     * 
     * @param p
     *            Player whos inventory will be closed
     */
    public void closeCrateInventory(Player p)
    {
	if (!isInInventory(p))
	    return;
	plugin.playersInInventory.remove(p);
	p.closeInventory();
    }

    /**
     * Checks if a player is viewing an inventory
     * 
     * @param p
     *            Player to check if inventory is open or not
     * @return If inventory is open
     */
    public boolean isInInventory(Player p)
    {
	return plugin.playersInInventory.contains(p);
    }
}
