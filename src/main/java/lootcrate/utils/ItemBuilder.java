package lootcrate.utils;

import lootcrate.LootCrate;
import lootcrate.gui.GUIItem;
import lootcrate.objects.CrateItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ItemBuilder {

    private LootCrate plugin;
    private ItemStack item;
    private String name;
    private CrateItem crateItem;
    private boolean hideAttributes;
    private boolean durabilitySet;
    private int durability;
    private boolean unbreakable;
    private List<String> lore = new ArrayList<String>();

    /**
     * Creates a new ItemBuilder with the specified material.
     *
     * @param material The material
     */
    public ItemBuilder(LootCrate plugin, Material material) {
        this.item = new ItemStack(material);
        this.plugin = plugin;
    }

    /**
     * Creates a new ItemBuilder with the specified material
     * and item data.
     *
     * @param material The material
     * @param data The item data
     */
    public ItemBuilder(LootCrate plugin, Material material, short data) {
        this.item = new ItemStack(material, 1, data);
        this.plugin = plugin;
    }

    public ItemBuilder(LootCrate plugin, ItemStack itemStack) {
        this.item = itemStack.clone();
        this.plugin = plugin;
    }

    /**
     * Sets the amount of the item.
     *
     * @param amount Item amount
     * @return This ItemBuilder
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setCrateItem(CrateItem crateItem) {
        this.crateItem = crateItem;
        return this;
    }

    /**
     * Sets the name of the item.
     *
     * @param name Item name
     * @return This ItemBuilder
     */
    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Adds an empty lore line.
     * This can be used to skip a line,
     * a.k.a. a spacer.
     *
     * @return This ItemBuilder
     */
    public ItemBuilder addLoreSpacer() {
        this.lore.add("");
        return this;
    }

    /**
     * Adds an empty lore line, but only if
     * the requirement boolean is true.
     * This can be used to skip a line,
     * a.k.a. a spacer.
     *
     * @param requirement The method is only executed if this is true
     * @return This ItemBuilder, even when requirement is false
     */
    public ItemBuilder addLoreSpacer(boolean requirement) {
        if(!requirement)
            return this;

        return this.addLoreSpacer();
    }

    /**
     * Adds the specified lore line to the
     * item. If there are newline characters
     * in the string, multiple lines are added.
     *
     * @param lore Line to add to the lore.
     * @return This ItemBuilder
     */
    public ItemBuilder addLore(String lore) {
        String[] lores = lore.split("\\\\n");
        for(String splitLore : lores) {
            this.lore.add(splitLore);
        }
        return this;
    }

    /**
     * Adds the specified lore line to the
     * item, but only if the requirement
     * boolean is true. If there are newline
     * characters in the string, multiple lines
     * are added.
     *
     * @param requirement The method is only executed if this is true
     * @param lore The lore line to add
     * @return This ItemBuilder, even when requirement is false
     */
    public ItemBuilder addLore(boolean requirement, String lore) {
        if(!requirement)
            return this;

        return this.addLore(lore);
    }

    /**
     * Hides this item's attributes, like damage
     * stats on swords.
     *
     * @return This ItemBuilder
     */
    public ItemBuilder hideAttributes() {
        this.hideAttributes = true;
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        this.durability = durability;
        this.durabilitySet = true;
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }



    /**
     * Returns an ItemStack with the settings
     * specified in this ItemBuilder.
     *
     * This returns a clone, so modifying
     * the ItemStack further afterwards does
     * not affect this ItemBuilder.
     *
     * @return A clone of the ItemStack
     */
    public ItemStack get() {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);
        if(hideAttributes) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        meta.setUnbreakable(unbreakable);

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey("lootcrate", "gadget-random");
        container.set(namespacedKey, PersistentDataType.INTEGER, new Random().nextInt(0, 500000));

        if(getCrateItem() != null) {
            NamespacedKey crateItemKey = new NamespacedKey("lootcrate", "crateitem-id");
            container.set(crateItemKey, PersistentDataType.INTEGER, getCrateItem().getId());
        }

        if(durabilitySet && meta instanceof Damageable) {
            ((Damageable) meta).setDamage(durability);
        }

        item.setItemMeta(meta);


        return item.clone();
    }

    public GUIItem getClickableItem(BiConsumer<ItemStack, UUID> onClick) {
        return new GUIItem(plugin, get(), onClick);
    }

    public CrateItem getCrateItem() {
        return crateItem;
    }
}
