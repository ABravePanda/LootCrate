package lootcrate.events.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import lootcrate.LootCrate;
import lootcrate.managers.UpdateManager;
import lootcrate.other.Permission;

public class PlayerJoinListener implements Listener
{
    
    private UpdateManager updateManager;

    public PlayerJoinListener(LootCrate plugin)
    {
	this.updateManager = plugin.updateManager;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
	Player p = e.getPlayer();
	
	if(!p.hasPermission(Permission.LOOTCRATE_UPDATE_NOTIFICATION.getKey()))
	    return;
	
	updateManager.sendNotificationPlayer(p);
	
	
	
    }
    
}
