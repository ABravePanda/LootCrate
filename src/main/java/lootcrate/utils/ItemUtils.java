package lootcrate.utils;

import lootcrate.LootCrate;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ItemUtils {
    public static ItemStack setDisplayName(ItemStack item, String displayName) {
        ItemMeta meta = item.getItemMeta();
        if (displayName != null)
            meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setEnchantment(ItemStack item, Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        if (lore != null)
            meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addCrateID(LootCrate plugin, ItemStack item, int id)
    {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-id");
        container.set(key, PersistentDataType.INTEGER, id);
        item.setItemMeta(meta);
        addRandomizer(plugin, item);
        return item;
    }

    public static ItemStack addRandomizer(LootCrate plugin, ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-randomizer");
        container.set(key, PersistentDataType.INTEGER, ObjUtils.randomID(5));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack removeRandomizer(LootCrate plugin, ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-randomizer");
        if(container.has(key, PersistentDataType.INTEGER))
            container.remove(key);
        item.setItemMeta(meta);
        return item;
    }

    public static int getIDFromItem(LootCrate plugin, ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-id");
        return container.get(key, PersistentDataType.INTEGER);
    }
}
