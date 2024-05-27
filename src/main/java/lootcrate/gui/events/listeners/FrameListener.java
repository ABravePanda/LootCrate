package lootcrate.gui.events.listeners;

import lootcrate.LootCrate;
import lootcrate.gui.Frame;
import lootcrate.gui.events.FrameCloseEvent;
import lootcrate.gui.events.FrameOpenEvent;
import lootcrate.managers.FrameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class FrameListener implements Listener {

    private LootCrate plugin;
    private FrameManager frameManager;

    public FrameListener(LootCrate plugin) {
        this.plugin = plugin;
        this.frameManager = plugin.getFrameManager();
    }

    @EventHandler
    public void onFrameOpen(FrameOpenEvent event) {
        System.out.println(event.getFrame());

        UUID uuid = event.getPlayerUUID();
        Frame frame = event.getFrame();
        Player player = getPlayer(uuid);

        if(frameManager.isFrameOpen(uuid)) {
            System.out.println("Frame already open");
        }

        frameManager.addOpenFrame(uuid, frame);

        frame.init();

        player.openInventory(frame.getInventory());
    }

    @EventHandler
    public void onFrameClose(FrameCloseEvent event) {

        UUID uuid = event.getPlayerUUID();
        Frame frame = event.getFrame();
        Player player = getPlayer(uuid);

        System.out.println("Manual Close " + event.isManual());

        if(event.isManual() && !frame.canBeManuallyClosed()) {
            //reopen frame
            player.openInventory(frame.getInventory());
            event.setCancelled(true);
            return;
        }

        frameManager.removeOpenFrame(uuid);

    }

    private Player getPlayer(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if(player == null)
            throw new IllegalStateException("Unable to find player with UUID: " + playerUUID);

        return player;
    }
}
