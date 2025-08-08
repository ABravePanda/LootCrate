package lootcrate.events.listeners;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Option;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.managers.KeyCacheManager;
import lootcrate.managers.MessageManager;
import lootcrate.managers.OptionManager;
import lootcrate.managers.UpdateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final OptionManager optionManager;
    private final UpdateManager updateManager;
    private final MessageManager messageManager;
    private final KeyCacheManager keyCacheManager;

    public PlayerJoinListener(LootCrate plugin) {
        this.updateManager = plugin.getManager(UpdateManager.class);
        this.optionManager = plugin.getManager(OptionManager.class);
        this.messageManager = plugin.getManager(MessageManager.class);
        this.keyCacheManager = plugin.getManager(KeyCacheManager.class);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (optionManager.valueOf(Option.JOIN_KEY_NOTIFICATION)){
            if(!keyCacheManager.hasKeys(p.getUniqueId()))
                return;
            else if(keyCacheManager.convertIntToCrate(p.getUniqueId()).isEmpty()) return;
            else
                messageManager.sendMessage(p, Message.JOIN_KEY_NOTIFICATION,  ImmutableMap.of(Placeholder.KEY_AMOUNT, keyCacheManager.convertIntToCrate(p.getUniqueId()).size() + ""));

        }

        if (!p.hasPermission(Permission.LOOTCRATE_UPDATE_NOTIFICATION.getKey()))
            return;

        if (optionManager.valueOf(Option.ADMIN_NOTIFICATIONS))
            updateManager.sendNotificationPlayer(p);

    }

}
