package lootcrate.gui.frames.menu;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import net.md_5.bungee.api.ChatColor;

public class CrateItemFrame extends BasicFrame
{

    private LootCrate plugin;
    private Crate crate;

    public CrateItemFrame(LootCrate plugin, Player p, Crate crate)
    {
	super(plugin, p, crate.getName());

	this.plugin = plugin;
	this.crate = crate;

	registerFrame();
	generateFrame();
	registerItems();
    }

    @Override
    public void generateFrame()
    {
	fillBackground(Material.WHITE_STAINED_GLASS_PANE);
	fillItems();
    }
    
    @Override
    public void unregisterFrame()
    {
	GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods

    public void fillBackground(Material m)
    {
	for (int i = 0; i < getInventory().getSize(); i++)
	{
	    this.setItem(i, new GUIItem(i,m));
	}
    }

    public void fillItems()
    {
	int index = 0;
	for(CrateItem item : crate.getItems())
	{
	    this.setItem(index, new GUIItem(index,item));
	    index++;
	}
    }


}
