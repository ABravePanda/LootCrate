package lootcrate.gui.items;


import java.util.concurrent.Callable;

import org.bukkit.inventory.ItemStack;

public class GUIItem
{
    private ItemStack item;
    
    public GUIItem()
    {
	
    }
    
    public void click(Callable<Integer> callable)
    {
	try
	{
	    callable.call();
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
}
