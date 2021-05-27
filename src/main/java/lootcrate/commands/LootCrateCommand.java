package lootcrate.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;

import lootcrate.LootCrate;
import lootcrate.commands.subs.SubCommandLootCrateAdd;
import lootcrate.commands.subs.SubCommandLootCrateCommand;
import lootcrate.commands.subs.SubCommandLootCrateCreate;
import lootcrate.commands.subs.SubCommandLootCrateDelete;
import lootcrate.commands.subs.SubCommandLootCrateDisplayChances;
import lootcrate.commands.subs.SubCommandLootCrateGive;
import lootcrate.commands.subs.SubCommandLootCrateGui;
import lootcrate.commands.subs.SubCommandLootCrateItems;
import lootcrate.commands.subs.SubCommandLootCrateKey;
import lootcrate.commands.subs.SubCommandLootCrateList;
import lootcrate.commands.subs.SubCommandLootCrateReload;
import lootcrate.commands.subs.SubCommandLootCrateRemove;
import lootcrate.commands.subs.SubCommandLootCrateSet;
import lootcrate.commands.subs.SubCommandLootCrateVersion;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;

public class LootCrateCommand extends Command
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

	if (args.length == 0)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
	    return;
	}

	switch (args[0].toLowerCase())
	{
	case "create":
	    new SubCommandLootCrateCreate(plugin, sender, args).runSubCommand(false);
	    break;
	case "key":
	    new SubCommandLootCrateKey(plugin, sender, args).runSubCommand(true);
	    break;
	case "add":
	    new SubCommandLootCrateAdd(plugin, sender, args).runSubCommand(true);
	    break;
	case "remove":
	    new SubCommandLootCrateRemove(plugin, sender, args).runSubCommand(false);
	    break;
	case "items":
	    new SubCommandLootCrateItems(plugin, sender, args).runSubCommand(false);
	    break;
	case "give":
	    new SubCommandLootCrateGive(plugin, sender, args).runSubCommand(false);
	    break;
	case "set":
	    new SubCommandLootCrateSet(plugin, sender, args).runSubCommand(true);
	    break;
	case "command":
	    new SubCommandLootCrateCommand(plugin, sender, args).runSubCommand(false);
	    break;
	case "reload":
	    new SubCommandLootCrateReload(plugin, sender, args).runSubCommand(false);
	    break;
	case "displaychances":
	    new SubCommandLootCrateDisplayChances(plugin, sender, args).runSubCommand(false);
	    break;
	case "version":
	    new SubCommandLootCrateVersion(plugin, sender, args).runSubCommand(false);
	    break;
	case "delete":
	    new SubCommandLootCrateDelete(plugin, sender, args).runSubCommand(false);
	    break;
	case "list":
	    new SubCommandLootCrateList(plugin, sender, args).runSubCommand(false);
	    break;
	case "gui":
	    new SubCommandLootCrateGui(plugin, sender, args).runSubCommand(false);
	    break;
	default:
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
	    break;
	}
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();
	if (args.length == 1)
	{
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_CREATE))
		list.add("create");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_DELETE))
		list.add("delete");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_ADD))
		list.add("add");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_REMOVE))
		list.add("remove");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_SET))
		list.add("set");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_GIVE))
		list.add("give");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_KEY))
		list.add("key");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_ITEMS))
		list.add("items");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_LIST))
		list.add("list");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_COMMAND))
		list.add("command");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_RELOAD))
		list.add("reload");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_DISPLAYCHANCES))
		list.add("displaychances");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_VERSION))
		list.add("version");
	    if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_GUI))
		list.add("gui");
	    return list;
	}

	list.clear();

	switch (args[0].toLowerCase())
	{
	case "create":
	    return new SubCommandLootCrateCreate(plugin, sender, args).runTabComplete();
	case "key":
	    return new SubCommandLootCrateKey(plugin, sender, args).runTabComplete();
	case "add":
	    return new SubCommandLootCrateAdd(plugin, sender, args).runTabComplete();
	case "remove":
	    return new SubCommandLootCrateRemove(plugin, sender, args).runTabComplete();
	case "items":
	    return new SubCommandLootCrateItems(plugin, sender, args).runTabComplete();
	case "give":
	    return new SubCommandLootCrateGive(plugin, sender, args).runTabComplete();
	case "set":
	    return new SubCommandLootCrateSet(plugin, sender, args).runTabComplete();
	case "command":
	    return new SubCommandLootCrateCommand(plugin, sender, args).runTabComplete();
	case "reload":
	    return new SubCommandLootCrateReload(plugin, sender, args).runTabComplete();
	case "displaychances":
	    return new SubCommandLootCrateDisplayChances(plugin, sender, args).runTabComplete();
	case "version":
	    return new SubCommandLootCrateVersion(plugin, sender, args).runTabComplete();
	case "delete":
	    return new SubCommandLootCrateDelete(plugin, sender, args).runTabComplete();
	case "list":
	    return new SubCommandLootCrateList(plugin, sender, args).runTabComplete();
	case "gui":
	    return new SubCommandLootCrateGui(plugin, sender, args).runTabComplete();
	default:
	    return list;
	}
    }

    public boolean hasPermission(CommandSender sender, Permission permission)
    {
	return this.hasPermission(sender, Permission.COMMAND_LOOTCRATE_ADMIN, permission);
    }

}
