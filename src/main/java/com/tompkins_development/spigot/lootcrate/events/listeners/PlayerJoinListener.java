package com.tompkins_development.spigot.lootcrate.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.enums.Option;
import com.tompkins_development.spigot.lootcrate.enums.Permission;
import com.tompkins_development.spigot.lootcrate.managers.OptionManager;
import com.tompkins_development.spigot.lootcrate.managers.UpdateManager;

public class PlayerJoinListener implements Listener
{
    private OptionManager optionManager;
    private UpdateManager updateManager;

    public PlayerJoinListener(LootCrate plugin)
    {
	this.updateManager = plugin.getUpdateManager();
	this.optionManager = plugin.getOptionManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
	Player p = e.getPlayer();

	if (!p.hasPermission(Permission.LOOTCRATE_UPDATE_NOTIFICATION.getKey()))
	    return;

	if (optionManager.valueOf(Option.ADMIN_NOTIFICATIONS))
	    updateManager.sendNotificationPlayer(p);

    }

}
