package com.tompkins_development.spigot.lootcrate.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.objects.CrateItem;

public class ObjUtils
{
    public static int randomID(int length)
    {
	int min = (int) Math.pow(10, length - 1);
	int max = (int) Math.pow(10, length);

	Random random = new Random();

	return random.nextInt(max - min) + min;
    }

    public static String getRandomString(int length)
    {
	String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	StringBuilder salt = new StringBuilder();
	Random rnd = new Random();
	while (salt.length() < length)
	{
	    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	    salt.append(SALTCHARS.charAt(index));
	}
	String saltStr = salt.toString();
	return saltStr;

    }

    public static ItemStack assignCrateToItem(LootCrate plugin, Crate crate)
    {
	ItemStack item = crate.getKey().getItem();
	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemMeta itemMeta = item.getItemMeta();
	itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, crate.getId());
	item.setItemMeta(itemMeta);
	return item;
    }

    public static CrateItem assignRandomIDToItem(LootCrate plugin, CrateItem crateItem)
    {
	ItemStack item = crateItem.getItem();
	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemMeta itemMeta = item.getItemMeta();
	itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, getRandomString(9));
	item.setItemMeta(itemMeta);
	crateItem.setItem(item);
	return crateItem;
    }

    public static boolean doKeysMatch(LootCrate plugin, ItemStack item, Crate crate)
    {
	if (!isKey(plugin, item))
	    return false;

	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemMeta meta = item.getItemMeta();
	PersistentDataContainer container = meta.getPersistentDataContainer();

	return container.get(key, PersistentDataType.INTEGER) == crate.getId();
    }

    public static boolean isKey(LootCrate plugin, ItemStack item)
    {
	if (item == null)
	    return false;
	if (item.getItemMeta() == null)
	    return false;

	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemMeta meta = item.getItemMeta();
	PersistentDataContainer container = meta.getPersistentDataContainer();

	if (container == null)
	    return false;
	if (!container.has(key, PersistentDataType.INTEGER))
	    return false;
	return container.get(key, PersistentDataType.INTEGER) != null;
    }

    public static Map<String, Object> MemoryToMap(MemorySection section)
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();
	if (section == null)
	    return map;

	for (String s : section.getKeys(false))
	    map.put(s, section.get(s));

	return map;
    }
}
