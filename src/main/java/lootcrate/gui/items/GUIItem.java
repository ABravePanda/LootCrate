package lootcrate.gui.items;


import java.util.Arrays;
import java.util.concurrent.Callable;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIItem implements Listener
{
    private ItemStack item;
    private Callable<Integer> function;
    
    public GUIItem(ItemStack item, String name, String... lore)
    {
	this.item = editMeta(item,name,lore);
    }
    
    public GUIItem(Material mat, String name, String... lore)
    {
	this.item = editMeta(new ItemStack(mat),name,lore);
    }
    
    public GUIItem(Material mat)
    {
	this.item = new ItemStack(mat);
    }
    
    public GUIItem(ItemStack item)
    {
	this.item = item;
    }
    
    public GUIItem() {}
    
    public ItemStack getItemStack()
    {
	return this.item;
    }
    
    public void setClickHandler(Callable<Integer> function)
    {
	this.function = function;
    }
    
    private ItemStack editMeta(ItemStack item, String name, String...lore)
    {
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName(name);
	meta.setLore(Arrays.asList(lore));
	item.setItemMeta(meta);
	return item;
    }

    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws Exception
    {
	if(e.getCurrentItem() == null) return;
	
	if(e.getCurrentItem().equals(getItemStack()))
	{
	    if(function != null)
		function.call();
	    e.setCancelled(true);
	}
    }
}
