package lootcrate.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.commands.subs.SubCommandLootCrateAdd;
import lootcrate.commands.subs.SubCommandLootCrateCreate;
import lootcrate.commands.subs.SubCommandLootCrateGet;
import lootcrate.commands.subs.SubCommandLootCrateItems;
import lootcrate.commands.subs.SubCommandLootCrateKey;
import lootcrate.commands.subs.SubCommandLootCrateRemove;
import lootcrate.commands.subs.SubCommandLootCrateSet;
import lootcrate.managers.CrateManager;
import lootcrate.managers.LocationManager;
import lootcrate.managers.MessageManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.other.Message;
import lootcrate.utils.CommandUtils;

public class LootCrateCommand implements CommandExecutor, TabCompleter
{
    private LootCrate plugin;
    private MessageManager messageManager;
    private CrateManager crateManager;
    private LocationManager locationManager;

    public LootCrateCommand(LootCrate plugin)
    {
	this.plugin = plugin;
	this.messageManager = plugin.messageManager;
	this.crateManager = plugin.crateManager;
	this.locationManager = plugin.locationManager;

	plugin.getCommand("lootcrate").setExecutor(this);
	plugin.getCommand("lootcrate").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

	Player p = (Player) sender;

	if (cmd.getName().equalsIgnoreCase("lootcrate"))
	{
	    // sender must be player
	    if (!(sender instanceof Player))
	    {
		messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
		return false;
	    }

	    if (args.length == 0)
	    {
		messageManager.sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
		return false;
	    }

	    if (args[0].equalsIgnoreCase("create"))
		new SubCommandLootCrateCreate(plugin, sender, args);

	    if (args[0].equalsIgnoreCase("key"))
		new SubCommandLootCrateKey(plugin, sender, args);

	    if (args[0].equalsIgnoreCase("add"))
		new SubCommandLootCrateAdd(plugin, sender, args);

	    if (args[0].equalsIgnoreCase("remove"))
		new SubCommandLootCrateRemove(plugin, sender, args);

	    if (args[0].equalsIgnoreCase("items"))
		new SubCommandLootCrateItems(plugin, sender, args);

	    if (args[0].equalsIgnoreCase("set"))
		new SubCommandLootCrateSet(plugin, sender, args);

	    if (args[0].equalsIgnoreCase("get"))
		new SubCommandLootCrateGet(plugin, sender, args);
		
	    // command command
	    if (args[0].equalsIgnoreCase("command"))
	    {
		Crate crate = crateManager.getCrateById(CommandUtils.tryParse(args[1]));
		CrateItem item = crateManager.getCrateItemById(crate, Integer.parseInt(args[2]));
		String command = CommandUtils.builder(args, 3);
		item.getCommands().add(command);
		crate.replaceItem(item);
		crateManager.save(crate);
	    }

	}

	return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
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
		list.add("[CrateID]");
	    return list;
	}
	if (args[0].equalsIgnoreCase("get"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    if (args.length == 3)
		list.add("(Amount)");
	    return list;
	}
	if (args[0].equalsIgnoreCase("set"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    return list;
	}
	if (args[0].equalsIgnoreCase("items"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    return list;
	}

	// others
	if (args[0].equalsIgnoreCase("add"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    if (args.length == 3)
		list.add("[MinimumAmount]");
	    if (args.length == 4)
		list.add("[MaximumAmount]");
	    if (args.length == 5)
		list.add("[Chance]");
	    if (args.length == 6)
		list.add("[IsDisplay]");
	    return list;
	}
	if (args[0].equalsIgnoreCase("key"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    if (args.length == 3)
		list.add("[IsGlowing]");
	    return list;
	}
	if (args[0].equalsIgnoreCase("command"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    if (args.length == 3)
		list.add("[ItemID]");
	    if (args.length == 4)
		list.add("[Command] - Use {player} as placeholder");
	    return list;
	}
	return list;
    }

}
