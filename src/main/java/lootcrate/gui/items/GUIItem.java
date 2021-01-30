package lootcrate.gui.items;

import java.util.Arrays;
import java.util.concurrent.Callable;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.objects.CrateItem;

public class GUIItem implements Listener
{
    private CrateItem crateItem;
    private ItemStack item;
    private Callable<Integer> function;

    public GUIItem(ItemStack item, String name, String... lore)
    {
	this.item = editMeta(item, name, lore);
    }

    public GUIItem(Material mat, String name, String... lore)
    {
	this.item = editMeta(new ItemStack(mat), name, lore);
    }

    public GUIItem(Material mat)
    {
	this.item = new ItemStack(mat);
    }

    public GUIItem(ItemStack item)
    {
	this.item = item;
    }
    
    public GUIItem(CrateItem item)
    {
	this.crateItem = item;
	this.item = crateItem.getItem();
    }

    public GUIItem()
    {
    }

    public ItemStack getItemStack()
    {
	return this.item;
    }
    
    public CrateItem getCrateItem()
    {
	return this.crateItem;
    }

    public void setClickHandler(Callable<Integer> function)
    {
	this.function = function;
    }

    private ItemStack editMeta(ItemStack item, String name, String... lore)
    {
	name = ChatColor.translateAlternateColorCodes('&', name);
	
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName(name);
	meta.setLore(Arrays.asList(lore));
	item.setItemMeta(meta);
	return item;
    }

    @EventHandler
    public void onGUIClick(GUIItemClickEvent e) throws Exception
    {
	if (e.getItem() != this)
	    return;

	if (function != null)
	    function.call();
	e.setCancelled(true);
    }
}
