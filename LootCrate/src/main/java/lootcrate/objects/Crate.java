package lootcrate.objects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import lootcrate.utils.ObjUtils;

public class Crate
{
    private int id;
    private String name;
    private CrateKey key;
    private List<CrateItem> items;
    private boolean displayItemChances = true;
    
    public Crate(String name, CrateKey key, List<CrateItem> items)
    {
	this.setId(ObjUtils.randomID(3));
	this.setName(name);
	this.setKey(key);
	if(items != null)
	    this.setItems(items);
	else
	    this.setItems(new ArrayList<CrateItem>());
    }
    
    public Crate(String name)
    {
	this.setId(ObjUtils.randomID(3));
	this.setName(name);
	this.setKey(null);
	this.setItems(new ArrayList<CrateItem>());
    }
    
    private int calculateChances()
    {
	int chance = 0;
	for(CrateItem item : getItems())
	{
	    chance+=item.getChance();
	}
	return chance;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public CrateKey getKey()
    {
	return key;
    }

    public void setKey(CrateKey key)
    {
	this.key = key;
    }

    public List<CrateItem> getItems()
    {
	return items;
    }
    
    public void addItem(CrateItem item)
    {
	System.out.println(getItems().size());
	getItems().add(item);
	System.out.println(getItems().size());
    }
    
    public CrateItem getItem(int id)
    {
	for(CrateItem item : getItems())
	{
	    if(item.getId() == id) return item;
	}
	return null;
    }
    
    public void removeItem(CrateItem item)
    {
	if(getItems().contains(item))
	    getItems().remove(item);
    }
    
    public void replaceItem(CrateItem item)
    {
	for(CrateItem item2 : getItems())
	{
	    if(item2.getId() == item.getId()) item2 = item;
	}
    }
    
    public Map<Integer, Map<String, Object>> getSeralizedItems()
    {
	Map<Integer, Map<String, Object>> map = new LinkedHashMap<Integer, Map<String, Object>>();
	if(getItems() == null) return map;
	for(CrateItem item : getItems())
	{
	    map.put(item.getId(), item.serialize());
	}
	return map;
    }
    
    public static List<CrateItem> getDeseralizedItems(MemorySection section)
    {
	System.out.println("run");
	List<CrateItem> item = new ArrayList<CrateItem>();
	for (String s : section.getKeys(false))
	{
	    System.out.println(s);
	    MemorySection itemSection = (MemorySection) section.get(s);
	    CrateItem item2 = CrateItem.deserialize(itemSection);
	    item.add(item2);
	    
	}
	return item;
    }

    public void setItems(List<CrateItem> items)
    {
	this.items = items;
    }

    public int getChanceCount()
    {
	return calculateChances();
    }
    
    public Map<String, Object> serialize()
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	map.put("Id", getId());
	map.put("Name", getName());
	map.put("DisplayChances", isDisplayItemChances());
	map.put("Key", getKey() != null ? getKey().serialize() : null);
	map.put("Items", getSeralizedItems());
	
	return map;
    }
    
    public static Crate deserialize(String location, FileConfiguration config)
    {
	Crate crate = new Crate(config.getString(location + ".Name"));
	crate.id = config.getInt(location + ".Id");
	crate.displayItemChances = config.getBoolean(location + ".DisplayChances");
	if(config.get(location + ".Items") instanceof ArrayList)
	    if(config.getList(location + ".Items").size() != 0);
	if(config.get(location + ".Items") != null)
	    crate.items = Crate.getDeseralizedItems((MemorySection) config.get(location + ".Items"));
	if(config.get(location + ".Key") != null)
	    crate.key = CrateKey.deserialize((MemorySection) config.get(location + ".Key"));
	return crate;
    }

    public boolean isDisplayItemChances()
    {
	return displayItemChances;
    }

    public void setDisplayItemChances(boolean displayItemChances)
    {
	this.displayItemChances = displayItemChances;
    }

}
