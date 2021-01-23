package com.tompkins_development.spigot.lootcrate.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.tompkins_development.spigot.lootcrate.LootCrate;

public class KeyModifier
{
    private LootCrate plugin;
    
    public KeyModifier(LootCrate plugin)
    {
	this.plugin = plugin;
    }
    
    public void addCodeToItem(ItemStack item)
    {
	ItemMeta meta = item.getItemMeta();
	if(meta.getPersistentDataContainer().get(plugin.getKey(), PersistentDataType.STRING) == null)
	    meta.getPersistentDataContainer().set(plugin.getKey(), PersistentDataType.STRING, "valid_key");
	item.setItemMeta(meta);
    }
}
