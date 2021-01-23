package com.tompkins_development.spigot.lootcrate.obj;

import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.utils.KeyModifier;

public class Key implements ConfigurationSerializable
{

    private UUID uuid;
    private ItemStack item;
    private LootCrate plugin;
    
    public Key(LootCrate plugin, ItemStack item)
    {
	this.setPlugin(plugin);
	this.setUuid(UUID.randomUUID());
	this.setItem(item);
    }

    public UUID getUuid()
    {
	return uuid;
    }

    public void setUuid(UUID uuid)
    {
	this.uuid = uuid;
    }

    public ItemStack getItem()
    {
	return item;
    }

    public void setItem(ItemStack item)
    {
	new KeyModifier(null).addCodeToItem(item);
	this.item = item;
    }

    public LootCrate getPlugin()
    {
	return plugin;
    }
    
    private void setPlugin(LootCrate plugin)
    {
	this.plugin = plugin;
    }
}
