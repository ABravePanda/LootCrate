package lootcrate.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean isFull(Inventory i) {
        return i.firstEmpty() == -1;
    }
}
