package lootcrate.gui.menu;

import lootcrate.LootCrate;
import lootcrate.gui.item.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class SimpleMenu implements Menu {

    private final LootCrate plugin;
    private final MenuItem[] items;
    private final Inventory inventory;

    public SimpleMenu(LootCrate plugin, Rows row, String title) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(this, row.getSize(), title);
        items = new MenuItem[row.getSize()];
    }

    /**
     *
     * @param player
     * @param slot
     * @param click
     * @return A boolean value on whether or not to cancel the click event
     * TODO: CHANGE
     */
    @Override
    public boolean click(Player player, int slot, ClickType click) {
        if(items[slot] == null) return true;

        MenuItem item = items[slot];
        Consumer<Player> action = item.getClickAction(click);
        action.accept(player);

        return true;
    }

    @Override
    public void setItem(int slot, MenuItem item) {
        items[slot] = item;
        inventory.setItem(slot, item.getStack());
    }

    @Override
    public abstract void onSetItems();

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public LootCrate getPlugin() {
        return plugin;
    }

    public enum Rows {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        private int size;

        Rows(int rows) {
            this.size = rows * 9;
        }

        public int getSize() {
            return size;
        }
    }
}
