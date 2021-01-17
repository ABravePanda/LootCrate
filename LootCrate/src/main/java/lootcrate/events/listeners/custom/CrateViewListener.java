package lootcrate.events.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.events.custom.CrateAccessEvent;
import lootcrate.events.custom.CrateOpenEvent;
import lootcrate.events.custom.CrateViewEvent;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;

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
	    plugin.invManager.openCrateInventory(e.getPlayer(), e.getCrate());
    }

}
