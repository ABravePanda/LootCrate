package com.tompkins_development.spigot.lootcrate.mgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.obj.Placeholder;

public class PlaceholderManager
{
    private LootCrate plugin;
    private List<Placeholder> placeholders;
    
    public PlaceholderManager(LootCrate plugin)
    {
	this.plugin = plugin;
	this.placeholders = new ArrayList<Placeholder>();
	keyExists("hi");
    }
    
    public void registerPlaceholder(Placeholder placeholder)
    {
	if(!keyExists(placeholder.getKey()))
		placeholders.add(placeholder);
    }
    
    private List<Placeholder> getPlaceholders()
    {
	return Collections.unmodifiableList(placeholders);
    }
    
    private boolean keyExists(String key)
    {
	for(Placeholder p : getPlaceholders())
	    if(p.getKey().equals(key)) return true;
	return false;

    }
}
