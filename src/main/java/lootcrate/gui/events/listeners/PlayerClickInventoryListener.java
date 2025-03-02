package lootcrate.gui.events.listeners;

import lootcrate.gui.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PlayerClickInventoryListener implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        final Inventory clickedInventory = e.getClickedInventory();
        final Inventory inventory = e.getInventory();

        if(clickedInventory == null) return;

        if(!(clickedInventory.getHolder() instanceof final Menu menu)) return;

        e.setCancelled(menu.click((Player) e.getWhoClicked(), e.getSlot(), e.getClick()));
    }
}
