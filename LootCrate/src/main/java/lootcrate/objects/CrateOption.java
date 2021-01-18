package lootcrate.objects;

import java.util.LinkedHashMap;
import java.util.Map;

import lootcrate.other.CrateOptionType;

public class CrateOption
{
    private CrateOptionType key;
    private Object value;
    
    public CrateOption(CrateOptionType key, Object value)
    {
	this.setKey(key);
	this.setValue(value);
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

    public Map<CrateOptionType, Object> serialize()
    {
	Map<CrateOptionType, Object> map = new LinkedHashMap<CrateOptionType, Object>();
	
	map.put(getKey(), getValue());
	
	return map;
    }
}
