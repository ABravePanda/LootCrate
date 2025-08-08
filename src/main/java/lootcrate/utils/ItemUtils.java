package lootcrate.utils;

import lootcrate.LootCrate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.io.File;
import lootcrate.managers.FileManager;

public class ItemUtils {
    public static ItemStack setDisplayName(ItemStack item, String displayName) {
        ItemMeta meta = getOrCreateItemMeta(item);
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
        ItemMeta meta = getOrCreateItemMeta(item);
        if (lore != null)
            meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addCrateID(LootCrate plugin, ItemStack item, int id)
    {
        ItemMeta meta = getOrCreateItemMeta(item);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-id");
        container.set(key, PersistentDataType.INTEGER, id);
        item.setItemMeta(meta);
        addRandomizer(plugin, item);
        return item;
    }

    public static ItemStack addRandomizer(LootCrate plugin, ItemStack item)
    {
        ItemMeta meta = getOrCreateItemMeta(item);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-randomizer");
        container.set(key, PersistentDataType.INTEGER, ObjUtils.randomID(5));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack removeRandomizer(LootCrate plugin, ItemStack item)
    {
        ItemMeta meta = getOrCreateItemMeta(item);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-randomizer");
        if(container.has(key, PersistentDataType.INTEGER))
            container.remove(key);
        item.setItemMeta(meta);
        return item;
    }

    public static int getIDFromItem(LootCrate plugin, ItemStack item)
    {
        ItemMeta meta = getOrCreateItemMeta(item);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "lootcrate-crate-id");
        return container.get(key, PersistentDataType.INTEGER);
    }

    public static ItemMeta getOrCreateItemMeta(ItemStack itemStack) {
        if(itemStack.hasItemMeta())
            return itemStack.getItemMeta();
        return Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    public static String getDisplayOrTranslatedName(LootCrate plugin, ItemStack item) {
        if (item == null) return "";
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        // Sinon, traduction via words.yml
        String type = item.getType().toString();
        FileManager fileManager = plugin.getManager(FileManager.class);
        File wordsFile = new File(plugin.getDataFolder(), "words.yml");
        if (!wordsFile.exists()) return type;
        org.bukkit.configuration.file.YamlConfiguration wordsConfig = fileManager.getConfiguration(wordsFile);
        String translated = wordsConfig.getString(type);
        return translated != null ? translated : type;
    }
}
