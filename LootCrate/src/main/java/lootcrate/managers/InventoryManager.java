package lootcrate.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
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

    LootCrate plugin;
    MessageManager manager;
    OptionManager optionManager;

    /**
     * Constructor for InventoryManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public InventoryManager(LootCrate plugin)
    {
	this.plugin = plugin;
	manager = plugin.messageManager;
	optionManager = plugin.optionManager;
    }

    /**
     * Opens the crate inventory for specified player
     * 
     * @param p
     *            Players who will view the inventory
     * @param crate
     *            Crate which should be opened
     */
    public void openCrateInventory(Player p, Crate crate)
    {
	if (isInInventory(p))
	    return;

	Inventory inv = Bukkit.createInventory(null, crate.getItems().size() > 26 ? 54 : 27, crate.getName());

	if (crate.getItems().size() > 54)
	    return;

	List<CrateItem> items = crate.getItems();
	Collections.sort(items);
	for (CrateItem item : items)
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
	    }
	    inv.addItem(itemStack);
	}
	plugin.playersInInventory.add(p);
	p.openInventory(inv);
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
