package lootcrate.gui.menu;

import lootcrate.gui.item.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface Menu extends InventoryHolder {

    boolean click(Player player, int slot, ClickType click);
    void setItem(int slot, MenuItem item);
    public MenuItem getItem(int slot);
    void onSetItems();
    void onClose();

    default void open(Player player) {
        onSetItems();
        player.openInventory(this.getInventory());
    }

    default void close(Player player) {
        onClose();
        player.closeInventory();
    }
}
