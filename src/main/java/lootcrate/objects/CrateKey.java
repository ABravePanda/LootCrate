package lootcrate.objects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CrateKey
{
    private ItemStack item;
    private boolean glowing;

    public CrateKey(ItemStack item, boolean glowing)
    {
	this.setItem(item);
	this.setGlowing(glowing);
    }

    public ItemStack getItem()
    {
	if (isGlowing())
	{
	    item.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
	    ItemMeta meta = item.getItemMeta();
	    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    item.setItemMeta(meta);
	}
	return item;
    }

    public void setItem(ItemStack item)
    {
	this.item = item;
    }

    public boolean isGlowing()
    {
	return glowing;
    }

    public void setGlowing(boolean glowing)
    {
	this.glowing = glowing;
    }

    public Map<String, Object> serialize()
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();

	map.put("Material", getItem().getType().toString());
	map.put("Name", getItem().getItemMeta().getDisplayName());
	map.put("Lore", getItem().getItemMeta().getLore());
	map.put("Glowing", isGlowing());

	return map;
    }


    public static CrateKey deserialize(MemorySection section)
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();

	for (String s : section.getKeys(false))
	    map.put(s, section.get(s));

	ItemStack item = new ItemStack(Material.valueOf((String) map.get("Material")));
	ItemMeta meta = item.getItemMeta();

	if (map.get("Name") != null)
	    meta.setDisplayName((String) map.get("Name"));
	if (map.get("Lore") != null)
	    meta.setLore((List<String>) map.get("Lore"));

	item.setItemMeta(meta);

	CrateKey key = new CrateKey(item, (Boolean) map.get("Glowing"));

	return key;
    }

    public static ItemStack deserializeEnchantments(ItemStack item, ConfigurationSection section)
    {
	for (String s : section.getKeys(false))
	{
	    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(s));
	    item.addEnchantment(enchantment, (Integer) section.get(s));
	}
	return item;
    }
}
