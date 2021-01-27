package lootcrate.commands.subs;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateReload implements SubCommand
{
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateReload(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if(playerRequired && !(sender instanceof Player))
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
	    return;
	}
	
	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_RELOAD.getKey()) && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}

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
