package lootcrate.commands.subs;

import java.util.LinkedList;
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
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateDisplayChances implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateDisplayChances(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand()
    {
	Player p = (Player) sender;

	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_DISPLAYCHANCES.getKey())
		&& !p.hasPermission(Permission.LOOTCRATE_INTERACT_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (args.length <= 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_DISPLAY_USAGE, null);
	    return;
	}

	if (CommandUtils.tryParse(args[1]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_DISPLAY_USAGE, null);
	    return;
	}
	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}

	crate.setDisplayItemChances(Boolean.parseBoolean(args[2]));
	plugin.crateManager.save(crate);
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_DISPLAY_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
			Placeholder.VALUE, Boolean.parseBoolean(args[2]) + ""));

	plugin.messageManager.crateNotification(crate, sender);
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
	if (args.length == 3)
	{
	    list.add("[Will Display Chances]");
	    list.add("true");
	    list.add("false");
	}
	return list;
    }

}
