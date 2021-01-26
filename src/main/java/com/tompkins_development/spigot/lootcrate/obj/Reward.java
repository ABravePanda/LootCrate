package com.tompkins_development.spigot.lootcrate.obj;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Reward implements ConfigurationSerializable
{
    private UUID uuid;
    private ItemStack item;
    private List<String> commands;
    private double chance;
    private int[] range;
    private boolean commandOnly;
    
    public Reward(ItemStack item, List<String> commands, double chance, int[] range, boolean commandOnly)
    {
	this.setUuid(UUID.randomUUID());
	this.setItem(item);
	this.setCommands(new LinkedList<String>());
	if(commands != null)
	    this.setCommands(commands);
	this.setChance(chance);
	this.setRange(range);
	this.setCommandOnly(commandOnly);
    }
    
    public Reward(Map<String, Object> data)
    {
	this.uuid = (UUID) data.get("uuid");
	this.item = (ItemStack) data.get("item");
	this.chance = (Double) data.get("chance");
	this.range = (int[]) data.get("range");
	this.commandOnly = (Boolean) data.get("commandonly");
	this.commands = data.get("commands") instanceof List ? (List<String>) data.get("commands") : new LinkedList<String>();
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
        this.item = item;
    }

    public List<String> getCommands()
    {
        return commands;
    }

    public void setCommands(List<String> commands)
    {
        this.commands = commands;
    }
    
    public void addCommand(String command)
    {
	getCommands().add(command);
    }
    
    public void removeCommand(int index)
    {
	if(index >= getCommands().size()) return;
	this.commands.remove(index);
    }

    public double getChance()
    {
        return chance;
    }

    public void setChance(double chance)
    {
        this.chance = chance;
    }

    public int[] getRange()
    {
	if(range.length != 2) return new int[]{1,1};
	if(range[0] > range[1]) return new int[]{1,1};
        return range;
    }

    public void setRange(int[] range)
    {
        this.range = range;
    }

    public boolean isCommandOnly()
    {
        return commandOnly;
    }

    public void setCommandOnly(boolean commandOnly)
    {
        this.commandOnly = commandOnly;
    }
    
    @Override
    public Map<String, Object> serialize()
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();
	map.put("uuid", getUuid());
	map.put("item", getItem().serialize());
	map.put("chance", getChance());
	map.put("range", getRange());
	map.put("commandonly", isCommandOnly());
	
	List<String> temp = new LinkedList<String>();
	for(String cmd : getCommands())
	    temp.add(cmd);
	
	map.put("commands", temp);
	return map;
    }
}
