package com.tompkins_development.spigot.lootcrate;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.tompkins_development.spigot.lootcrate.obj.Crate;
import com.tompkins_development.spigot.lootcrate.obj.Key;
import com.tompkins_development.spigot.lootcrate.obj.Reward;

public class LootCrate extends JavaPlugin
{
    private NamespacedKey namespacedKey = new NamespacedKey(this, "lootcrate");
    
    @Override
    public void onEnable()
    {
    }
    
    @Override
    public void onDisable()
    {
    }
    
    public NamespacedKey getKey()
    {
	return namespacedKey;
    }
    
    private void registerSerialization()
    {
	ConfigurationSerialization.registerClass(Reward.class);
	ConfigurationSerialization.registerClass(Crate.class);
	ConfigurationSerialization.registerClass(Key.class);
    }
}
