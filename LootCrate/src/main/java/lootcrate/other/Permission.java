package lootcrate.other;

public enum Permission
{
    COMMAND_META("lootcrate.meta"),
    COMMAND_LOOTCRATE_CREATE("lootcrate.create"),
    COMMAND_LOOTCRATE_KEY("lootcrate.key"),
    COMMAND_LOOTCRATE_ADD("lootcrate.add"),
    COMMAND_LOOTCRATE_REMOVE("lootcrate.remove"),
    COMMAND_LOOTCRATE_ITEMS("lootcrate.items"),
    COMMAND_LOOTCRATE_SET("lootcrate.set"),
    COMMAND_LOOTCRATE_GET("lootcrate.get"),
    COMMAND_LOOTCRATE_COMMAND("lootcrate.command"),
    COMMAND_LOOTCRATE_RELOAD("lootcrate.reload");

    String key;
    
    Permission(String key)
    {
	this.key = key;
    }

    public String getKey()
    {
	return this.key;
    }
}
