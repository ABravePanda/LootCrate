package lootcrate.utils;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class ObjUtils {
    public static int randomID(int length) {
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length);

        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    public static String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static ItemStack assignCrateToKey(LootCrate plugin, Crate crate) {
        ItemStack item = crate.getKey().getItem();
        if(item ==null) return null;
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
        ItemMeta itemMeta = ItemUtils.getOrCreateItemMeta(item);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, crate.getId());
        item.setItemMeta(itemMeta);
        ItemUtils.removeRandomizer(plugin, item);
        return item;
    }

    public static CrateItem assignRandomIDToItem(LootCrate plugin, CrateItem crateItem) {
        ItemStack item = crateItem.getItem();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
        ItemMeta itemMeta = ItemUtils.getOrCreateItemMeta(item);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, getRandomString(9));
        item.setItemMeta(itemMeta);
        crateItem.setItem(item);
        return crateItem;
    }

    public static boolean doKeysMatch(LootCrate plugin, ItemStack item, Crate crate) {
        if (!isKey(plugin, item))
            return false;

        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
        ItemMeta meta = ItemUtils.getOrCreateItemMeta(item);
        PersistentDataContainer container = meta.getPersistentDataContainer();

        return container.get(key, PersistentDataType.INTEGER) == crate.getId();
    }

    public static boolean isKey(LootCrate plugin, ItemStack item) {
        if (item == null)
            return false;
        if (ItemUtils.getOrCreateItemMeta(item) == null)
            return false;

        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
        ItemMeta meta = ItemUtils.getOrCreateItemMeta(item);
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (container == null)
            return false;
        if (!container.has(key, PersistentDataType.INTEGER))
            return false;
        return container.get(key, PersistentDataType.INTEGER) != null;
    }

    public static Map<String, Object> MemoryToMap(MemorySection section) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (section == null)
            return map;

        for (String s : section.getKeys(false))
            map.put(s, section.get(s));

        return map;
    }
}
