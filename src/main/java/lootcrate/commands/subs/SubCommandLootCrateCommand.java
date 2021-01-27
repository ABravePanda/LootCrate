package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateCommand implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateCommand(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if(playerRequired && !(sender instanceof Player))
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
	    return;
	}
	
	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_COMMAND.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}

	if (args.length <= 3)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_COMMAND_USAGE, null);
	    return;
	}

	if (CommandUtils.tryParse(args[1]) == null || CommandUtils.tryParse(args[2]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_COMMAND_USAGE, null);
	    return;
	}

	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	CrateItem item = plugin.crateManager.getCrateItemById(crate, Integer.parseInt(args[2]));
	if (item == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_ITEM_NOT_FOUND,
		    ImmutableMap.of(Placeholder.ITEM_ID, "" + CommandUtils.tryParse(args[2])));
	    return;
	}
	String command = CommandUtils.builder(args, 3);
	item.getCommands().add(command);
	crate.replaceItem(item);
	plugin.crateManager.save(crate);

	plugin.crateManager.save(crate);
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_COMMAND_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
			Placeholder.ITEM_ID, "" + item.getId(), Placeholder.ITEM_TYPE, "" + item.getItem().getType(),
			Placeholder.ITEM_NAME, item.getItem().getItemMeta().getDisplayName()));

	plugin.messageManager.crateNotification(crate, sender);
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();
	
	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_COMMAND.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;
	
	if (args.length == 2)
	{
	    list.add("[CrateID]");
	    TabUtils.addCratesToList(list, plugin.crateManager);
	}
	if (args.length == 3)
	{
	    list.add("[ItemID]");
	    TabUtils.addCrateItemsToListFromID(list, plugin.crateManager, Integer.parseInt(args[1]));
	}
	if (args.length == 4)
	    list.add("[Command] - Use {player} as placeholder");
	return list;
    }

}
