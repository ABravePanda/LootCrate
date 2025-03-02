package lootcrate.utils;

import com.google.common.collect.Lists;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class ItemBuilder implements Cloneable {
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilder(Material material, int amount, short durability) {
        this(new ItemStack(material, amount, durability));
    }

    public ItemBuilder clone() {
        return new ItemBuilder(this.itemStack);
    }

    public ItemStack toItemStack() {
        return this.itemStack;
    }

    public ItemStack build() {
        return this.itemStack;
    }

    public ItemBuilder withItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemBuilder withMaterial(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder withAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder withDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder withInfiniteDurability() {
        this.itemStack.setDurability((short)32767);
        return this;
    }

    public ItemBuilder withName(String displayName) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        }

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public String getName() {
        ItemMeta meta = this.itemStack.getItemMeta();
        return meta != null ? meta.getDisplayName() : null;
    }

    public ItemBuilder withEmptyName() {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
        }

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.setLore(lore);
        }

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        return this.withLore(Arrays.asList(lore));
    }

    public ItemBuilder withLoreLine(String line) {
        List<String> lore = this.getLore();
        if (lore != null) {
            lore.add(line);
            this.withLore(lore);
        } else {
            this.withLore(line);
        }

        return this;
    }

    public ItemBuilder withLoreLines(String... lines) {
        return this.withLoreLines(Arrays.asList(lines));
    }

    public ItemBuilder withLoreLines(List<String> lines) {
        List<String> lore = this.getLore();
        if (lore != null) {
            lore.addAll(lines);
            this.withLore(lore);
        } else {
            this.withLore(lines);
        }

        return this;
    }

    public List<String> getLore() {
        ItemMeta meta = this.itemStack.getItemMeta();
        return (List)(meta != null ? meta.getLore() : Lists.newArrayList());
    }

    public ItemBuilder removeLore() {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.setLore((List)null);
        }

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withItemFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags);
        }

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.removeItemFlags(flags);
        }

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment ench, int level) {
        this.itemStack.removeEnchantment(ench);
        this.itemStack.addEnchantment(ench, level);
        return this;
    }

    public ItemBuilder withUnsafeEnchantment(Enchantment ench, int level) {
        this.itemStack.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        this.itemStack.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder withDyeColor(DyeColor color) {
        this.itemStack.setDurability((short)color.getDyeData());
        return this;
    }

    public ItemBuilder withLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta meta = (LeatherArmorMeta)this.itemStack.getItemMeta();
            if (meta != null) {
                meta.setColor(color);
            }

            this.itemStack.setItemMeta(meta);
        } catch (ClassCastException var3) {
        }

        return this;
    }

    public ItemBuilder withPotionType(PotionEffectType type) {
        try {
            PotionMeta meta = (PotionMeta)this.itemStack.getItemMeta();
            if (meta != null) {
                meta.setMainEffect(type);
            }

            this.itemStack.setItemMeta(meta);
        } catch (ClassCastException var3) {
        }

        return this;
    }

    public ItemBuilder withCustomPotionEffect(PotionEffect effect, boolean overwrite) {
        try {
            PotionMeta meta = (PotionMeta)this.itemStack.getItemMeta();
            if (meta != null) {
                meta.addCustomEffect(effect, overwrite);
            }

            this.itemStack.setItemMeta(meta);
        } catch (ClassCastException var4) {
        }

        return this;
    }

    public ItemBuilder removeCustomPotionEffect(PotionEffectType type) {
        try {
            PotionMeta meta = (PotionMeta)this.itemStack.getItemMeta();
            if (meta != null) {
                meta.removeCustomEffect(type);
            }

            this.itemStack.setItemMeta(meta);
        } catch (ClassCastException var3) {
        }

        return this;
    }

    public ItemBuilder clearCustomPotionEffects() {
        try {
            PotionMeta meta = (PotionMeta)this.itemStack.getItemMeta();
            if (meta != null) {
                meta.clearCustomEffects();
            }

            this.itemStack.setItemMeta(meta);
        } catch (ClassCastException var2) {
        }

        return this;
    }


    public String getSkullOwner() {
        try {
            SkullMeta meta = (SkullMeta)this.itemStack.getItemMeta();
            if (meta != null) {
                return meta.getOwner();
            }
        } catch (ClassCastException var2) {
        }

        return null;
    }

    public ItemBuilder withSkullTexture(String texture) {
        if (this.itemStack.getType() != Material.PLAYER_HEAD) {
            this.itemStack.setType(Material.PLAYER_HEAD); // Ensure it's a skull item
        }

        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        if (skullMeta != null) {
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(new URL(texture)); // Set the custom skin
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return this;
            }

            profile.setTextures(textures);
            skullMeta.setOwnerProfile(profile);
            this.itemStack.setItemMeta(skullMeta); // Set the meta back to the ItemStack
        }
        return this; // Return the builder instance
    }


    public ItemBuilder withData(MaterialData data) {
        this.itemStack.setData(data);
        return this;
    }

    public ItemBuilder withMeta(ItemMeta meta) {
        this.itemStack.setItemMeta(meta);
        return this;
    }
}