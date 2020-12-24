package lootcrate.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.commands.subs.SubCommandLootCrateAdd;
import lootcrate.commands.subs.SubCommandLootCrateCommand;
import lootcrate.commands.subs.SubCommandLootCrateCreate;
import lootcrate.commands.subs.SubCommandLootCrateDisplayChances;
import lootcrate.commands.subs.SubCommandLootCrateGet;
import lootcrate.commands.subs.SubCommandLootCrateItems;
import lootcrate.commands.subs.SubCommandLootCrateKey;
import lootcrate.commands.subs.SubCommandLootCrateReload;
import lootcrate.commands.subs.SubCommandLootCrateRemove;
import lootcrate.commands.subs.SubCommandLootCrateSet;
import lootcrate.commands.subs.SubCommandLootCrateVersion;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.utils.TabUtils;
import lootcrate.utils.interfaces.Command;

public class LootCrateCommand implements Command
{

    private LootCrate plugin;
    private String[] args;
    private CommandSender sender;

    public LootCrateCommand(LootCrate plugin, String[] args, CommandSender sender)
    {
	this.plugin = plugin;
	this.args = args;
	this.sender = sender;
    }

    @Override
    public void executeCommand()
    {
	if (!(sender instanceof Player) && args.length == 1 ? !args[0].equalsIgnoreCase("reload") ? true : false
		: false)
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
	    return;
	}

	if (args.length == 0)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
	    return;
	}

	if (args[0].equalsIgnoreCase("create"))
	    new SubCommandLootCrateCreate(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("key"))
	    new SubCommandLootCrateKey(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("add"))
	    new SubCommandLootCrateAdd(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("remove"))
	    new SubCommandLootCrateRemove(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("items"))
	    new SubCommandLootCrateItems(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("get"))
	    new SubCommandLootCrateGet(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("set"))
	    new SubCommandLootCrateSet(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("command"))
	    new SubCommandLootCrateCommand(plugin, sender, args);

	else if (args[0].equalsIgnoreCase("reload"))
	    new SubCommandLootCrateReload(plugin, sender, args);
	
	else if (args[0].equalsIgnoreCase("displaychances"))
	    new SubCommandLootCrateDisplayChances(plugin, sender, args);
	
	else if (args[0].equalsIgnoreCase("version"))
	    new SubCommandLootCrateVersion(plugin, sender, args);

	else
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();
	if (args.length == 1)
	{
	    list.add("create");
	    list.add("delete");
	    list.add("add");
	    list.add("remove");
	    list.add("set");
	    list.add("get");
	    list.add("key");
	    list.add("items");
	    list.add("list");
	    list.add("command");
	    list.add("reload");
	    list.add("displaychances");
	    list.add("version");
	    return list;
	}

	list.clear();

	// two arg commands
	if (args[0].equalsIgnoreCase("create"))
	{
	    if (args.length == 2)
		list.add("[CrateName]");
	    return list;
	}
	if (args[0].equalsIgnoreCase("delete"))
	{
	    if (args.length == 2)
	    {
		list.add("[CrateID]");
		TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    return list;
	}
	if (args[0].equalsIgnoreCase("get"))
	{
	    if (args.length == 2)
	    {
		list.add("[CrateID]");
		TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    if (args.length == 3)
		list.add("(Amount)");
	    return list;
	}
	if (args[0].equalsIgnoreCase("set"))
	{
	    if (args.length == 2)
	    {
		list.add("[CrateID]");
		TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    return list;
	}
	if (args[0].equalsIgnoreCase("items"))
	{
	    if (args.length == 2)
	    {
		list.add("[CrateID]");
		for (Crate crate : plugin.crateManager.load())
		    TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    return list;
	}

	// others
	if (args[0].equalsIgnoreCase("add"))
	{
	    if (args.length == 2)
	    {
		list.add("[CrateID]");
		TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    if (args.length == 3)
		list.add("[MinimumAmount]");
	    if (args.length == 4)
		list.add("[MaximumAmount]");
	    if (args.length == 5)
		list.add("[Chance]");
	    if (args.length == 6)
	    {
		list.add("[Is Display]");
		list.add("true");
		list.add("false");
	    }

	    return list;
	}
	if (args[0].equalsIgnoreCase("key"))
	{
	    if (args.length == 2)
	    {
		list.add("[CrateID]");
		TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    if (args.length == 3)
	    {
		list.add("[Is Glowing]");
		list.add("true");
		list.add("false");
	    }
	    return list;
	}
	if (args[0].equalsIgnoreCase("command"))
	{
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
	if (args[0].equalsIgnoreCase("displaychances"))
	{
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
		
	}
	return list;
    }

}
