package lootcrate.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public static boolean isFull(Inventory i) {
        for (ItemStack item : i.getStorageContents())
            if (item == null)
                return false;
        return true;
    }
}
