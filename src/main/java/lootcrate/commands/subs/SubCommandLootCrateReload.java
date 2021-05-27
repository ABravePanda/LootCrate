package lootcrate.commands.subs;

import java.util.List;

import org.bukkit.command.CommandSender;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;

public class SubCommandLootCrateReload extends SubCommand
{
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateReload(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_RELOAD, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if (this.testPlayer(playerRequired))
	    return;
	if (!this.testPermissions())
	    return;

	plugin.reloadConfig();
	plugin.reload();

	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_RELOAD_SUCCESS, null);
    }

    @Override
    public List<String> runTabComplete()
    {
	return null;
    }

}
