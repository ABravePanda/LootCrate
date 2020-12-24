package lootcrate.other;

public enum Permission
{
    COMMAND_META("lootcrate.command.meta"),
    COMMAND_LOOTCRATE_CREATE("lootcrate.command.lootcrate.create"),
    COMMAND_LOOTCRATE_KEY("lootcrate.command.lootcrate.key"),
    COMMAND_LOOTCRATE_ADD("lootcrate.command.lootcrate.add"),
    COMMAND_LOOTCRATE_REMOVE("lootcrate.command.lootcrate.remove"),
    COMMAND_LOOTCRATE_ITEMS("lootcrate.command.lootcrate.items"),
    COMMAND_LOOTCRATE_SET("lootcrate.command.lootcrate.set"),
    COMMAND_LOOTCRATE_GET("lootcrate.command.lootcrate.get"),
    COMMAND_LOOTCRATE_COMMAND("lootcrate.command.lootcrate.command"),
    COMMAND_LOOTCRATE_RELOAD("lootcrate.command.lootcrate.reload"),
    COMMAND_LOOTCRATE_DISPLAYCHANCES("lootcrate.command.lootcrate.displaychances"),
    COMMAND_LOOTCRATE_VERSION("lootcrate.command.lootcrate.version"),
    COMMAND_LOOTCRATE_ADMIN("lootcrate.command.*"),
    LOOTCRATE_INTERACT_ADMIN("lootcrate.interact.*");

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
