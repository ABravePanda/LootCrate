package com.tompkins_development.spigot.lootcrate.mgr;

import com.tompkins_development.spigot.lootcrate.LootCrate;

public class ManagerManager
{
    private LootCrate plugin;
    private FileManager fileManager;
    private PlaceholderManager placeholderManager;
    private CrateCacheManager crateCacheManager;
    
    public ManagerManager(LootCrate plugin)
    {
	this.plugin = plugin;
    }
    
    public void init()
    {
	this.fileManager = new FileManager(plugin);
	this.placeholderManager = new PlaceholderManager(plugin);
	this.crateCacheManager = new CrateCacheManager(plugin);
    }
    
    public FileManager getFileManager()
    {
	return fileManager;
    }   
    
    public PlaceholderManager getPlaceholderManager()
    {
	return placeholderManager;
    }
    
    public CrateCacheManager getCrateCacheManager()
    {
	return crateCacheManager;
    }
}
