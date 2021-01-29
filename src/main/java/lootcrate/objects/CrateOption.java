package lootcrate.objects;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import lootcrate.other.CrateOptionType;

public class CrateOption implements ConfigurationSerializable
{
    private CrateOptionType key;
    private Object value;
    
    public CrateOption(CrateOptionType key, Object value)
    {
	this.setKey(key);
	this.setValue(value);
    }
    
    public CrateOption(Map<String,Object> data)
    {
	this.key = CrateOptionType.valueOf((String) data.keySet().toArray()[0]);
	this.value = data.get(getKey());
    }
    
    public CrateOptionType getKey()
    {
	return key;
    }

    public void setKey(CrateOptionType key)
    {
	this.key = key;
    }

    public Object getValue()
    {
	return value;
    }

    public void setValue(Object value)
    {
	this.value = value;
    }

    public Map<String, Object> serialize()
    {
	Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	map.put("DUH", getValue());
	
	return map;
    }
}
