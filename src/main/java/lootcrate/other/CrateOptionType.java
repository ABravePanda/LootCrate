package lootcrate.other;

public enum CrateOptionType
{
    DISPLAY_CHANCES("Display-Chances", DataType.BOOLEAN),
    KNOCK_BACK("Knockback", DataType.DOUBLE),
    OPEN_SOUND("Open-Sound", DataType.STRING),
    OPEN_MESSAGE("Open-Message", DataType.STRING),
    HOLOGRAM_LINES("Hologram-Lines", DataType.LIST),
    HOLOGRAM_OFFSET_X("Hologram-Offset-X", DataType.BOOLEAN),
    HOLOGRAM_OFFSET_Y("Hologram-Offset-Y", DataType.BOOLEAN),
    HOLOGRAM_OFFSET_Z("Hologram-Offset-Z", DataType.BOOLEAN),
    ANIMATION_STYLE("Animation-Style", DataType.STRING);
    // CRATE_FORMAT("Crate-Format", DataType.MAP);

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
	for (CrateOptionType value : CrateOptionType.values())
	{
	    if (key.equals(value.getKey()))
		return value;
	}
	return null;
    }
}
