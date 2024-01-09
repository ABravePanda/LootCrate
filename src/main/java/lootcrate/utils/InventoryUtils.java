package lootcrate.utils;

import lootcrate.enums.SortType;
import lootcrate.objects.CrateItem;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventoryUtils {
    public static boolean isFull(Inventory i) {
        return i.firstEmpty() == -1;
    }

    public static void sort(List<CrateItem> items, SortType sortType) {
        switch (sortType) {
            case NONE -> Collections.sort(items);
            case CHANCE -> items.sort((item1, item2) -> {
                // Compare items based on their chance in descending order
                return Double.compare(item2.getChance(), item1.getChance());
            });
            case NAME -> {
                items.sort(Comparator.comparing(item -> {
                    String whatToSortBy = item.getItem().getItemMeta().getDisplayName().isBlank() ? item.getItem().getType().name() : item.getItem().getItemMeta().getDisplayName();
                    return whatToSortBy;
                }));
            }
            case ID -> items.sort((item1, item2) -> {
                // Compare items based on their chance in descending order
                return Integer.compare(item2.getId(), item1.getId());
            });
        }
    }


}
