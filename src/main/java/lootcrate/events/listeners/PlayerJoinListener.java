package lootcrate.events.listeners;

import lootcrate.LootCrate;
import lootcrate.enums.Option;
import lootcrate.enums.Permission;
import lootcrate.managers.OptionManager;
import lootcrate.managers.UpdateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final OptionManager optionManager;
    private final UpdateManager updateManager;

    public PlayerJoinListener(LootCrate plugin) {
        this.updateManager = plugin.getUpdateManager();
        this.optionManager = plugin.getOptionManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission(Permission.LOOTCRATE_UPDATE_NOTIFICATION.getKey()))
            return;

        if (optionManager.valueOf(Option.ADMIN_NOTIFICATIONS))
            updateManager.sendNotificationPlayer(p);

    }

}
