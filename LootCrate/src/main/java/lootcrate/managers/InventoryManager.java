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
import lootcrate.utils.ObjUtils;
import net.md_5.bungee.api.ChatColor;

public class InventoryManager
{

    LootCrate plugin;
    
    public InventoryManager(LootCrate plugin)
    {
	this.plugin = plugin;
    }
    
    public void openCrateInventory(Player p, Crate crate)
    {
	if(isInInventory(p)) return;
	
	Inventory inv = Bukkit.createInventory(null, crate.getItems().size() > 26 ? 54 : 27, crate.getName());
	if(crate.getItems().size() > 54) return;
	List<CrateItem> items = crate.getItems();
	Collections.sort(items);
	for(CrateItem item : items)
	{
	    item = ObjUtils.assignRandomIDToItem(plugin, item);
	    ItemStack itemStack = item.getItem().clone();
	    ItemMeta meta = itemStack.getItemMeta();
	    
	    List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
	    lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Chance: " + ChatColor.RED + item.getChance() + "%");
	    meta.setLore(lore);
	    itemStack.setItemMeta(meta);
	    inv.addItem(itemStack);
	}
	plugin.playersInInventory.add(p);
	p.openInventory(inv);
    }
    
    public void closeCrateInventory(Player p)
    {
	if(!isInInventory(p)) return;
	plugin.playersInInventory.remove(p);
	p.closeInventory();
    }
    
    public boolean isInInventory(Player p)
    {
	return plugin.playersInInventory.contains(p);
    }
}
