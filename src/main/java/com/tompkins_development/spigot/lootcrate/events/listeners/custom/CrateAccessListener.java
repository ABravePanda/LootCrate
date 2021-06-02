package com.tompkins_development.spigot.lootcrate.events.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import com.google.common.collect.ImmutableMap;
import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.enums.Message;
import com.tompkins_development.spigot.lootcrate.enums.Placeholder;
import com.tompkins_development.spigot.lootcrate.events.custom.CrateAccessEvent;
import com.tompkins_development.spigot.lootcrate.events.custom.CrateOpenEvent;
import com.tompkins_development.spigot.lootcrate.events.custom.CrateViewEvent;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.utils.CommandUtils;

public class CrateAccessListener implements Listener
{
    private LootCrate plugin;

    public CrateAccessListener(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    @EventHandler
    public void onAccess(CrateAccessEvent e)
    {
	Player p = e.getPlayer();
	Crate crate = e.getCrate();
	Action action = e.getAction();

	// if player has permission to interact with the crate
	if (!CommandUtils.hasCratePermission(crate, p))
	{
	    plugin.getMessageManager().sendMessage(p, Message.NO_PERMISSION_LOOTCRATE_INTERACT,
		    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
	    return;
	}

	// if left click, call open event
	if (action == Action.LEFT_CLICK_BLOCK)
	{
	    CrateViewEvent event = new CrateViewEvent(crate, p);
	    Bukkit.getPluginManager().callEvent(event);
	    if (event.isCancelled())
		e.setCancelled(true);
	}

	// if it was right click, check if key
	if (action == Action.RIGHT_CLICK_BLOCK)
	{
	    CrateOpenEvent event = new CrateOpenEvent(crate, p);
	    Bukkit.getPluginManager().callEvent(event);
	    if (event.isCancelled())
		e.setCancelled(true);
	}

    }

}
