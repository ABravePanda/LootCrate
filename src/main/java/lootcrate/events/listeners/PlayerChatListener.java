package lootcrate.events.listeners;

import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.enums.CrateOptionType;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final LootCrate plugin;

    public PlayerChatListener(LootCrate plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (!plugin.getChatManager().hasState(p))
            return;

        ChatState state = plugin.getChatManager().getState(p);
        Crate crate = state.getCrate();
        Crate finalCrate;


        e.setCancelled(true);
        plugin.getCacheManager().update(crate);
        plugin.getChatManager().removePlayer(p);

    }

    private void sendConfirmation(Player p, String message) {
        p.sendMessage(message);
    }

}
