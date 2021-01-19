package lootcrate.utils;

import java.util.List;

import lootcrate.managers.CrateManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

public class TabUtils
{

    public static void addCratesToList(List<String> list, CrateManager manager)
    {
	for (Crate crate : manager.load())
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
    
    public static void addCrateItemsToListFromID(List<String> list, CrateManager manager, int id)
    {
	Crate crate = manager.getCrateById(id);
	if(crate == null) return;
	
	for (CrateItem item : crate.getItems())
	{
	    list.add(item.getId() + "");
	}
    }
}
