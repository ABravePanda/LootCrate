package lootcrate.events.listeners;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Option;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.managers.OptionManager;
import lootcrate.managers.UpdateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final LootCrate plugin;
    private final OptionManager optionManager;
    private final UpdateManager updateManager;

    public PlayerJoinListener(LootCrate plugin) {
        this.plugin = plugin;
        this.updateManager = plugin.getUpdateManager();
        this.optionManager = plugin.getOptionManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (optionManager.valueOf(Option.JOIN_KEY_NOTIFICATION)){
            if(!plugin.getKeyCacheManager().hasKeys(p.getUniqueId()))
                return;
            else if(plugin.getKeyCacheManager().convertIntToCrate(p.getUniqueId()).size() == 0) return;
            else
                plugin.getMessageManager().sendMessage(p, Message.JOIN_KEY_NOTIFICATION,  ImmutableMap.of(Placeholder.KEY_AMOUNT, plugin.getKeyCacheManager().convertIntToCrate(p.getUniqueId()).size() + ""));

        }

        if (!p.hasPermission(Permission.LOOTCRATE_UPDATE_NOTIFICATION.getKey()))
            return;

        if (optionManager.valueOf(Option.ADMIN_NOTIFICATIONS))
            updateManager.sendNotificationPlayer(p);

    }

}
