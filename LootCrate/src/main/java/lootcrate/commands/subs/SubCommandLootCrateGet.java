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
import lootcrate.utils.CommandUtils;
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
	
	if (args.length != 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
	    return;
	}
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_SET.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}

	Location l = p.getTargetBlock(null, 10).getLocation();
	ImmutableMap<String, String> map1 = ImmutableMap.of("X", l.getBlockX() + "", "Y", l.getBlockY() + "",
		"Z", l.getBlockZ() + "");
	if (args[1].equalsIgnoreCase("none"))
	{
	    plugin.locationManager.removeCrateLocation(l);
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_REMOVE_SUCCESS, map1);
	    return;
	}

	if (CommandUtils.tryParse(args[1]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
	    return;
	}

	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	ImmutableMap<String, String> map = ImmutableMap.of("id", crate.getId() + "", "name", crate.getName(),
		"X", l.getBlockX() + "", "Y", l.getBlockY() + "", "Z", l.getBlockZ() + "");

	plugin.locationManager.addCrateLocation(l, crate);
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_SUCCESS, map);
    }
    
    
}
