package lootcrate.events.listeners.custom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import lootcrate.LootCrate;
import lootcrate.events.custom.CrateViewEvent;
import lootcrate.gui.frames.CrateViewFrame;

public class CrateViewListener implements Listener
{
    private LootCrate plugin;

    public CrateViewListener(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    @EventHandler
    public void onView(CrateViewEvent e)
    {
	    CrateViewFrame frame = new CrateViewFrame(plugin, e.getPlayer(), e.getCrate());
	    frame.open();
    }

}
