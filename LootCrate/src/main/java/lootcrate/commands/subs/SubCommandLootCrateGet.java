package lootcrate.commands.subs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
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
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.ObjUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateGet implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    
    public SubCommandLootCrateGet(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
	runSubCommand();
    }

    @Override
    public void runSubCommand()
    {
	Player p = (Player) sender;
	
	
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_GET.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (args.length < 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
	    return;
	}
	if (CommandUtils.tryParse(args[1]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
	    return;
	}

	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	
	if(crate.getKey() == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.KEY_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1]), Placeholder.CRATE_NAME, "" + crate.getName()));
	    return;
	}

	if (args.length == 3)
	{
	    if (CommandUtils.tryParse(args[2]) == null)
	    {
		plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
		return;
	    }
	    for (int i = 0; i < CommandUtils.tryParse(args[2]); i++)
	    {
		p.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
	    }

	} else
	    p.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName()));

    }
    
    
}
