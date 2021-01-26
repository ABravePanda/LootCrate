package com.tompkins_development.spigot.lootcrate.obj;

public class Placeholder<T>
{
    private String key;
    private Object value;
    private final Class<T> type;

    public Placeholder(String key, Object value, Class<T> type)
    {
	this.key = key;
	this.value = value;
	this.type = type;
    }

    public Class<T> getType()
    {
	return this.type;
    }
    
    public String getKey()
    {
	return this.key;
    }
    
    public Object getValue()
    {
	return this.value;
    }
    
    public String getStringValue()
    {
	return String.valueOf(getValue());
    }
    
    public int getIntValue()
    {
	return Integer.valueOf(getStringValue());
    }
    
    public boolean getBooleanValue()
    {
	return Boolean.valueOf(getStringValue());
    }
    
    public double getDoubleValue()
    {
	return Double.valueOf(getStringValue());
    }
    
    public Crate getCrateValue()
    {
	//TODO getCrateByObj
	return null;
    }
   
    
}
