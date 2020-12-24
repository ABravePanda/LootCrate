package lootcrate.other;

public enum Option
{
    DISPLAY_CHANCE("display-chance", DataType.BOOLEAN),
    DISPATCH_COMMAND_ITEM_AMOUNT("dispatch-command-item-time", DataType.BOOLEAN);
    
    String key;
    DataType type;

    Option(String key, DataType type)
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
}
