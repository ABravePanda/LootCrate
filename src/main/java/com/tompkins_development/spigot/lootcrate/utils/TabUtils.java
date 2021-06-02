package com.tompkins_development.spigot.lootcrate.utils;

import java.util.List;

import com.tompkins_development.spigot.lootcrate.managers.CacheManager;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.objects.CrateItem;

public class TabUtils
{

    public static void addCratesToList(List<String> list, CacheManager manager)
    {
	for (Crate crate : manager.getCache())
	{
	    list.add(crate.getId() + "");
	}
    }

    public static void addCrateItemsToList(List<String> list, Crate crate)
    {
	for (CrateItem item : crate.getItems())
	{
	    list.add(item.getId() + "");
	}
    }

    public static void addCrateItemsToListFromID(List<String> list, CacheManager manager, int id)
    {
	Crate crate = manager.getCrateById(id);
	if (crate == null)
	    return;

	for (CrateItem item : crate.getItems())
	{
	    list.add(item.getId() + "");
	}
    }
}
