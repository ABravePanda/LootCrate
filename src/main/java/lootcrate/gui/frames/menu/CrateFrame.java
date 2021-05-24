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
import net.md_5.bungee.api.ChatColor;

public class CrateFrame extends BasicFrame implements Listener
{

    private LootCrate plugin;
    private Crate crate;

    public CrateFrame(LootCrate plugin, Player p, Crate crate)
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
	fillOptions();
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
	    this.setItem(i, new GUIItem(i, m));
	}
    }

    public void fillOptions()
    {
	this.setItem(10, new GUIItem(10,Material.BRICKS, ChatColor.RED + "Items", ChatColor.GRAY + "All the items in the crate."));
	this.setItem(13, new GUIItem(13,Material.TRIPWIRE_HOOK, ChatColor.RED + "Key", ChatColor.GRAY + "Used to unlock the crate."));
	this.setItem(16, new GUIItem(16,Material.ENDER_PEARL, ChatColor.RED + "Locations", ChatColor.GRAY + "All the places the crate is set."));
	this.setItem(31, new GUIItem(31,Material.ANVIL, ChatColor.RED + "Options", ChatColor.GRAY + "Edit crate name, hologram, knockback, etc..."));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e)
    {
	if(!e.sameFrame(this)) return;
	
	Player p = e.getPlayer();
	ItemStack item = e.getItem().getItemStack();
	
	Frame frameToOpen = null;
	
	switch(item.getType())
	{
	case BRICKS:
	    frameToOpen = new CrateItemFrame(plugin, p, crate);
	    break;
	case TRIPWIRE_HOOK:
	    frameToOpen = new CrateKeyFrame(plugin, p, crate);
	    break;
	case ENDER_PEARL:
	    frameToOpen = new CrateLocationFrame(plugin, p, crate);
	    break;
	case ANVIL:
	    break;
	default: return;
	}

	this.close();
	frameToOpen.open();
    }

}
