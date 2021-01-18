package lootcrate.objects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import lootcrate.utils.ObjUtils;

public class CrateItem implements Comparable<CrateItem>
{
    private int id;
    private ItemStack item;
    private int chance;
    private int minAmount;
    private int maxAmount;
    private List<String> commands;
    private boolean isDisplay;

    public CrateItem(ItemStack item, int minAmount, int maxAmount, int chance, boolean isDisplay, List<String> commands)
    {
	this.setId(ObjUtils.randomID(5));
	this.setItem(item);
	this.setChance(chance);
	this.setMinAmount(minAmount);
	this.setMaxAmount(maxAmount);
	this.setDisplay(isDisplay);
	if(commands != null)
	    this.setCommands(commands);
	else
	    this.setCommands(new ArrayList<String>());
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public ItemStack getItem()
    {

	return item;
    }

    public void setItem(ItemStack item)
    {
	this.item = item;
    }

    public int getChance()
    {
	return chance;
    }

    public void setChance(int chance)
    {
	this.chance = chance;
    }

    public int getMinAmount()
    {
	return minAmount;
    }

    public void setMinAmount(int minAmount)
    {
	this.minAmount = minAmount;
    }

    public int getMaxAmount()
    {
	return maxAmount;
    }

    public void setMaxAmount(int maxAmount)
    {
	this.maxAmount = maxAmount;
    }

    public List<String> getCommands()
    {
	return commands;
    }

    public void setCommands(List<String> commands)
    {
	this.commands = commands;
    }

    public Map<String, Object> serialize()
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();

	map.put("ID", getId());
	map.put("Item", getItem().serialize());
	map.put("Chance", getChance());
	map.put("MinAmount", getMinAmount());
	map.put("MaxAmount", getMaxAmount());
	map.put("isDisplay", isDisplay());
	map.put("Commands", getCommands());

	return map;
    }

    @SuppressWarnings("unchecked")
    public static CrateItem deserialize(MemorySection section)
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();

	for (String s : section.getKeys(false))
	    map.put(s, section.get(s));

	
	if(map.size() == 0) return null;
	
	ItemStack item = ItemStack.deserialize(section.getConfigurationSection("Item").getValues(true));
	
	CrateItem crateItem = new CrateItem(item, (Integer) map.get("MinAmount"), (Integer) map.get("MaxAmount"),(Integer) map.get("Chance"), (Boolean) map.get("isDisplay"), (List<String>) map.get("Commands"));
	crateItem.setId((Integer) map.get("ID"));
	
	return crateItem;
    }

    private Map<String, Integer> serializeEnchantments()
    {
	Map<String, Integer> map = new LinkedHashMap<String, Integer>();
	for (Enchantment e : getItem().getEnchantments().keySet())
	{
	    map.put(e.getKey().getKey(), (Integer) getItem().getEnchantmentLevel(e));
	}
	return map;
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

    public boolean isDisplay()
    {
	return isDisplay;
    }

    public void setDisplay(boolean isDisplay)
    {
	this.isDisplay = isDisplay;
    }

    @Override
    public int compareTo(CrateItem o)
    {
	return Integer.valueOf(this.getChance()).compareTo(Integer.valueOf(o.getChance()));
    }

}
