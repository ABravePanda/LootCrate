package lootcrate.utils;

import java.util.Arrays;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils
{
    public static ItemStack setDisplayName(ItemStack item, String displayName)
    {
	ItemMeta meta = item.getItemMeta();
	if (displayName != null)
	    meta.setDisplayName(displayName);
	item.setItemMeta(meta);
	return item;
    }

    public static ItemStack setEnchantment(ItemStack item, Enchantment enchantment, int level)
    {
	item.addUnsafeEnchantment(enchantment, level);
	return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore)
    {
	ItemMeta meta = item.getItemMeta();
	if (lore != null)
	    meta.setLore(Arrays.asList(lore));
	item.setItemMeta(meta);
	return item;
    }
}
