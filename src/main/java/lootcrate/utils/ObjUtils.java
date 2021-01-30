package lootcrate.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

public class ObjUtils
{
    public static int randomID(int length) {
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length); // bound is exclusive

        Random random = new Random();

        return random.nextInt(max - min) + min;
    }
    
    public static String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    
    public static ItemStack assignCrateToItem(LootCrate plugin, Crate crate)
    {
	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemStack item = crate.getKey().getItem();
	ItemMeta itemMeta = item.getItemMeta();
	itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, crate.getId());
	item.setItemMeta(itemMeta);
	return item;
    }
    
    public static CrateItem assignRandomIDToItem(LootCrate plugin, CrateItem crateItem)
    {
	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemStack item = crateItem.getItem();
	ItemMeta itemMeta = item.getItemMeta();
	itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, getRandomString(9));
	item.setItemMeta(itemMeta);
	return crateItem;
    }
    
    public static boolean doKeysMatch(LootCrate plugin, ItemStack item, Crate crate)
    {
	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemMeta meta = item.getItemMeta();
	if(meta.getPersistentDataContainer() == null) return false;
	if(meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) == null) return false;
	return meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) == crate.getId();
    }
    
    public static boolean isKey(LootCrate plugin, ItemStack item)
    {
	NamespacedKey key = new NamespacedKey(plugin, "lootcrate-key");
	ItemMeta meta = item.getItemMeta();
	if(meta == null) return false;
	if(meta.getPersistentDataContainer() == null) return false;
	return meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) != null;
    }
    
    public static Map<String,Object> MemoryToMap(MemorySection section)
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();
	if(section == null) return map;

	for (String s : section.getKeys(false))
	    map.put(s, section.get(s));
	
	return map;
    }
}
