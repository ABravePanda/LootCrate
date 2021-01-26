package com.tompkins_development.spigot.lootcrate.obj;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Crate implements ConfigurationSerializable
{
    private UUID uuid;
    private String name;
    private Key key;
    private List<Reward> rewards;
    
    public Crate(String name, Key key, List<Reward> rewards)
    {
	this.setUuid(UUID.randomUUID());
	this.setName(name);
	this.setKey(key);
	this.setRewards(rewards);
    }

    public UUID getUuid()
    {
	return uuid;
    }

    public void setUuid(UUID uuid)
    {
	this.uuid = uuid;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public Key getKey()
    {
	return key;
    }

    public void setKey(Key key)
    {
	this.key = key;
    }

    public List<Reward> getRewards()
    {
	return rewards;
    }

    public void setRewards(List<Reward> rewards)
    {
	this.rewards = rewards;
    }

    @Override
    public Map<String, Object> serialize()
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();
	map.put("uuid", getUuid());
	map.put("name", getName());
	map.put("key", getKey());
	map.put("rewards", getRewards());
	return null;
    }
}
