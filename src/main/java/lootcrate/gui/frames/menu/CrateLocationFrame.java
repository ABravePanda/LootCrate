package lootcrate.gui.frames.menu;

import java.util.concurrent.Callable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import net.md_5.bungee.api.ChatColor;

public class CrateLocationFrame extends BasicFrame
{
    private Player p;
    private LootCrate plugin;
    private Crate crate;

    public CrateLocationFrame(LootCrate plugin, Player p, Crate crate)
    {
	super(plugin, p, crate.getName());

	this.plugin = plugin;
	this.crate = crate;
	this.p = p;

	registerFrame();
	generateFrame();
	registerItems();
    }

    @Override
    public void generateFrame()
    {
	fillBackground(Material.WHITE_STAINED_GLASS_PANE);
	fillLocations();
    }

    @Override
    public void unregisterFrame()
    {
	GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods


    public void fillLocations()
    {
	int index = 0;
	for (final Location l : plugin.locationManager.getCrateLocations(crate))
	{
	    GUIItem item = new GUIItem(index, l.getBlock().getType(),
		    ChatColor.GOLD + "" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ(),
		    ChatColor.AQUA + "" + l.getWorld().getName());

	    item.setClickHandler(new Callable<Integer>()
	    {
		public Integer call()
		{
		    p.teleport(l);
		    return 1;
		}
	    });

	    this.setItem(index, item);
	}
    }

}
