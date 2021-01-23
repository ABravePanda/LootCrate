package com.tompkins_development.spigot.lootcrate.mgr;

import com.tompkins_development.spigot.lootcrate.LootCrate;

public class ManagerManager
{
    private LootCrate plugin;
    private FileManager fileManager;
    private CrateCacheManager crateCacheManager;
    
    public ManagerManager(LootCrate plugin)
    {
	this.plugin = plugin;
	this.fileManager = new FileManager(plugin);
	this.crateCacheManager = new CrateCacheManager(plugin);
    }
    
    public FileManager getFileManager()
    {
	return fileManager;
    }
    
    public CrateCacheManager getCrateCacheManager()
    {
	return crateCacheManager;
    }
}
