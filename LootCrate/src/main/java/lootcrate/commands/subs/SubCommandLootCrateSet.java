package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.managers.HologramManager;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateSet implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    private HologramManager holoManager;
    
    public SubCommandLootCrateSet(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
	this.holoManager = plugin.holoManager;
    }

    @Override
    public void runSubCommand()
    {
	Player p = (Player) sender;
	
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_SET.getKey()) && !p.hasPermission(Permission.LOOTCRATE_INTERACT_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	
	if (args.length != 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
	    return;
	}

	Location l = p.getTargetBlock(null, 10).getLocation();
	ImmutableMap<Placeholder, String> map1 = ImmutableMap.of(Placeholder.X, l.getBlockX() + "", Placeholder.Y, l.getBlockY() + "",
		Placeholder.Z, l.getBlockZ() + "");
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
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	ImmutableMap<Placeholder, String> map = ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName(),
		Placeholder.X, l.getBlockX() + "", Placeholder.Y, l.getBlockY() + "", Placeholder.Z, l.getBlockZ() + "");

	plugin.locationManager.addCrateLocation(l, crate);
	
	//create hologram
	if(plugin.holoHook())
	    holoManager.createHologram(l.getBlock(), crate);
	
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_SUCCESS, map);
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();
	 if (args.length == 2)
	    {
		list.add("[CrateID]");
		TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    return list;
    }
    
    
}
