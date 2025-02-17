package lootcrate.gui.events.listeners;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUICloseEvent;
import lootcrate.managers.InventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GUICloseListener implements Listener {

    private final LootCrate plugin;

    public GUICloseListener(LootCrate plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClose(GUICloseEvent e) {
        plugin.getManager(InventoryManager.class).closeFrame(e.getPlayer(), e.getFrame());
    }

}
