package lootcrate.gui.items;

import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.objects.CrateItem;
import lootcrate.objects.CrateKey;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class GUIItem implements Listener {
    private CrateItem crateItem;
    private ItemStack item;
    private int slot;
    private Callable<Integer> function;

    public GUIItem(int slot, ItemStack item, String name, String... lore) {
        this.slot = slot;
        this.item = editMeta(item, name, lore);
    }

    public GUIItem(int slot, ItemStack item, String name) {
        this.slot = slot;
        this.item = editMeta(item, name);
    }

    public GUIItem(int slot, Material mat, String name, String... lore) {
        this.slot = slot;
        this.item = editMeta(new ItemStack(mat), name, lore);
    }

    public GUIItem(int slot, Material mat, String name) {
        this.slot = slot;
        this.item = editMeta(new ItemStack(mat), name);
    }

    public GUIItem(int slot, Material mat) {
        this.slot = slot;
        this.item = new ItemStack(mat);
    }

    public GUIItem(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
    }

    public GUIItem(int slot, CrateItem item) {
        this.slot = slot;
        this.crateItem = item;
        this.item = crateItem.getItem();
    }

    public GUIItem(int slot, CrateKey key) {
        this.slot = slot;
        this.item = key.getItem();
    }

    public GUIItem() {
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public CrateItem getCrateItem() {
        return this.crateItem;
    }

    public void setCrateItem(CrateItem item) {
        this.crateItem = item;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setClickHandler(Callable<Integer> function) {
        this.function = function;
    }

    private ItemStack editMeta(ItemStack item, String name, String... lore) {
        name = ChatColor.translateAlternateColorCodes('&', name);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack editMeta(ItemStack item, String name) {
        name = ChatColor.translateAlternateColorCodes('&', name);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public void setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    public void addLoreLine(String line) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
        lore.add(line);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void setLoreColor(ChatColor color)
    {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
        for(int i = 0; i < lore.size(); i++)
            lore.set(i, color + lore.get(i));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void setNameColor(ChatColor color)
    {
        setName(color + ChatColor.stripColor(item.getItemMeta().getDisplayName()));
    }

    public void setGlowing(boolean glowing)
    {
        if(glowing) {
            item.addUnsafeEnchantment(Enchantment.MENDING, 1);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        } else
            if(item.containsEnchantment(Enchantment.MENDING))
            item.removeEnchantment(Enchantment.MENDING);
    }

    @EventHandler
    public void onGUIClick(GUIItemClickEvent e) throws Exception {
        if(this != e.getItem()) return;
        if (function != null)
            function.call();
        e.setCancelled(true);
    }
}
