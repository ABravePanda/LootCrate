package lootcrate.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;
    private final JavaPlugin plugin;

    public ItemBuilder(Material material, JavaPlugin plugin) {
        this.item = new ItemStack(material);
        this.plugin = plugin;
        this.meta = ItemUtils.getOrCreateItemMeta(item);
    }

    public ItemBuilder(ItemStack item, JavaPlugin plugin) {
        this.item = item.clone();
        this.plugin = plugin;
        this.meta = ItemUtils.getOrCreateItemMeta(item);
    }

    public ItemBuilder name(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder lore(String... lines) {
        return lore(Arrays.asList(lines));
    }

    public ItemBuilder lore(List<String> lines) {
        meta.setLore(lines);
        return this;
    }

    public ItemBuilder addLore(String... lines) {
        List<String> lore = meta.getLore() != null ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        lore.addAll(Arrays.asList(lines));
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder enchant(Enchantment ench, int level, boolean unsafe) {
        if (unsafe) item.addUnsafeEnchantment(ench, level);
        else meta.addEnchant(ench, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment ench) {
        meta.removeEnchant(ench);
        return this;
    }

    public ItemBuilder clearEnchants() {
        meta.getEnchants().keySet().forEach(meta::removeEnchant);
        return this;
    }

    public ItemBuilder flag(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder clearFlags() {
        meta.removeItemFlags(ItemFlag.values());
        return this;
    }

    public ItemBuilder modelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public ItemBuilder glow() {
        enchant(Enchantment.AQUA_AFFINITY, 1, true);
        flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder persistent(String key, String value) {
        getPDC().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        return this;
    }

    public ItemBuilder persistent(String key, int value) {
        getPDC().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER, value);
        return this;
    }

    public ItemBuilder persistent(String key, boolean value) {
        getPDC().set(new NamespacedKey(plugin, key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
        return this;
    }

    public boolean hasPersistent(String key, PersistentDataType<?, ?> type) {
        return getPDC().has(new NamespacedKey(plugin, key), type);
    }
//
//    public <T, Z> T getPersistent(String key, PersistentDataType<T, Z> type) {
//        return getPDC().get(new NamespacedKey(plugin, key), type);
//    }

    public ItemBuilder removePersistent(String key) {
        getPDC().remove(new NamespacedKey(plugin, key));
        return this;
    }

    public ItemBuilder skullOwner(String playerName) {
        if (meta instanceof SkullMeta skull) {
            skull.setOwner(playerName);
        }
        return this;
    }


    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    private PersistentDataContainer getPDC() {
        return meta.getPersistentDataContainer();
    }
}
