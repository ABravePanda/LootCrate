package lootcrate.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.commands.subs.SubCommandLootCrateAdd;
import lootcrate.commands.subs.SubCommandLootCrateCommand;
import lootcrate.commands.subs.SubCommandLootCrateCreate;
import lootcrate.commands.subs.SubCommandLootCrateDelete;
import lootcrate.commands.subs.SubCommandLootCrateDisplayChances;
import lootcrate.commands.subs.SubCommandLootCrateGet;
import lootcrate.commands.subs.SubCommandLootCrateItems;
import lootcrate.commands.subs.SubCommandLootCrateKey;
import lootcrate.commands.subs.SubCommandLootCrateReload;
import lootcrate.commands.subs.SubCommandLootCrateRemove;
import lootcrate.commands.subs.SubCommandLootCrateSet;
import lootcrate.commands.subs.SubCommandLootCrateVersion;
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
	    new SubCommandLootCrateCreate(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("key"))
	    new SubCommandLootCrateKey(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("add"))
	    new SubCommandLootCrateAdd(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("remove"))
	    new SubCommandLootCrateRemove(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("items"))
	    new SubCommandLootCrateItems(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("get"))
	    new SubCommandLootCrateGet(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("set"))
	    new SubCommandLootCrateSet(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("command"))
	    new SubCommandLootCrateCommand(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("reload"))
	    new SubCommandLootCrateReload(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("displaychances"))
	    new SubCommandLootCrateDisplayChances(plugin, sender, args).runSubCommand();

	else if (args[0].equalsIgnoreCase("version"))
	    new SubCommandLootCrateVersion(plugin, sender, args).runSubCommand();
	
	else if (args[0].equalsIgnoreCase("delete"))
	    new SubCommandLootCrateDelete(plugin, sender, args).runSubCommand();

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

	if (args[0].equalsIgnoreCase("create"))
	    return new SubCommandLootCrateCreate(plugin, sender, args).runTabComplete();

	if (args[0].equalsIgnoreCase("delete"))
	    return new SubCommandLootCrateDelete(plugin, sender, args).runTabComplete();
	
	if (args[0].equalsIgnoreCase("get"))
	    return new SubCommandLootCrateGet(plugin, sender, args).runTabComplete();

	if (args[0].equalsIgnoreCase("set"))
	    return new SubCommandLootCrateSet(plugin, sender, args).runTabComplete();

	if (args[0].equalsIgnoreCase("items"))
	    return new SubCommandLootCrateItems(plugin, sender, args).runTabComplete();

	if (args[0].equalsIgnoreCase("add"))
	    return new SubCommandLootCrateAdd(plugin, sender, args).runTabComplete();

	if (args[0].equalsIgnoreCase("key"))
	    return new SubCommandLootCrateKey(plugin, sender, args).runTabComplete();

	if (args[0].equalsIgnoreCase("command"))
	    return new SubCommandLootCrateCommand(plugin, sender, args).runTabComplete();
	
	if (args[0].equalsIgnoreCase("displaychances"))
	    return new SubCommandLootCrateDisplayChances(plugin, sender, args).runTabComplete();
	
	return list;
    }

}
