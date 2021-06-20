package lootcrate.utils;

import lootcrate.managers.CacheManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

import java.util.List;

public class TabUtils {

    public static void addCratesToList(List<String> list, CacheManager manager) {
        for (Crate crate : manager.getCache()) {
            list.add(crate.getId() + "");
        }
    }

    public static void addCrateItemsToList(List<String> list, Crate crate) {
        for (CrateItem item : crate.getItems()) {
            list.add(item.getId() + "");
        }
    }

    public static void addCrateItemsToListFromID(List<String> list, CacheManager manager, int id) {
        Crate crate = manager.getCrateById(id);
        if (crate == null)
            return;

        for (CrateItem item : crate.getItems()) {
            list.add(item.getId() + "");
        }
    }
}
