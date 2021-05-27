package lootcrate.commands.subs;

import java.util.List;

import org.bukkit.command.CommandSender;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.managers.UpdateManager;

public class SubCommandLootCrateVersion extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    private UpdateManager updateManager;

    public SubCommandLootCrateVersion(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_VERSION, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
	this.updateManager = plugin.getUpdateManager();
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if (this.testPlayer(playerRequired))
	    return;
	if (!this.testPermissions())
	    return;

	if (args.length != 1)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_VERION_USAGE, null);
	    return;
	}

	updateManager.sendNotificationCommandSender(sender);

    }

    @Override
    public List<String> runTabComplete()
    {
	return null;
    }

}
