package lootcrate.gui.events.listeners;

import lootcrate.LootCrate;
import lootcrate.gui.Frame;
import lootcrate.managers.FrameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    private LootCrate plugin;
    private FrameManager frameManager;

    public InventoryCloseListener(LootCrate plugin) {
        this.plugin = plugin;
        this.frameManager = plugin.getFrameManager();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(!frameManager.isFrameOpen(event.getPlayer().getUniqueId())) {
            return;
        }

        Frame frame = frameManager.getOpenFrame(event.getPlayer().getUniqueId());
        frameManager.closeFrame(event.getPlayer().getUniqueId(), !frame.readyToClose());
    }
}
