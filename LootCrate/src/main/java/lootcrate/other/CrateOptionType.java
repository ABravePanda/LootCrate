package lootcrate.other;

public enum CrateOptionType
{
    DISPLAY_CHANCES("DisplayChances", DataType.BOOLEAN),
    KNOCK_BACK("Knockback", DataType.DOUBLE);
    
    String key;
    DataType type;

    CrateOptionType(String key, DataType type)
    {
	this.key = key;
	this.type = type;
    }

    public String getKey()
    {
	return this.key;
    }
    
    public DataType getType()
    {
	return this.type;
    }
    
    @Override
    public String toString()
    {
	return key;
    }
    
    public static CrateOptionType fromKey(String key)
    {
	for(CrateOptionType value : CrateOptionType.values())
	{
	    if(key.equals(value.getKey())) return value;
	}
	return null;
    }
}
