package lootcrate.commands.subs;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.CrateKey;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateReload implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateReload(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand()
    {
	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_RELOAD.getKey()) && !sender.hasPermission(Permission.LOOTCRATE_INTERACT_ADMIN.getKey()))
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
	// TODO Auto-generated method stub
	return null;
    }

}
