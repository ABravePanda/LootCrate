package lootcrate.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.managers.CrateManager;
import lootcrate.managers.LocationManager;
import lootcrate.managers.MessageManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.CrateKey;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.ObjUtils;

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
	// sender must be player
	if (!(sender instanceof Player))
	{
	    messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
	    return false;
	}

	Player p = (Player) sender;

	if (cmd.getName().equalsIgnoreCase("lootcrate"))
	{
	    if (args.length == 0)
	    {
		messageManager.sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
		return false;
	    }

	    // create command
	    if (args[0].equalsIgnoreCase("create"))
	    {
		if (args.length <= 1)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_USAGE, null);
		    return false;
		}
		if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_CREATE.getKey()))
		{
		    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
		    return false;
		}
		Crate crate = new Crate(CommandUtils.builder(args, 1));
		crateManager.save(crate);
		messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_SUCCESS,
			ImmutableMap.of("name", crate.getName(), "id", "" + crate.getId()));
	    }

	    // key command
	    if (args[0].equalsIgnoreCase("key"))
	    {
		if (args.length <= 2)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_USAGE, null);
		    return false;
		}
		if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_KEY.getKey()))
		{
		    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
		    return false;
		}
		if (p.getInventory().getItemInMainHand().getType() == Material.AIR)
		{
		    messageManager.sendMessage(sender, Message.MUST_HOLD_ITEM, null);
		    return false;
		}
		if (CommandUtils.tryParse(args[1]) == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_USAGE, null);
		    return false;
		}
		Crate crate = crateManager.getCrateById(CommandUtils.tryParse(args[1]));
		if (crate == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
			    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
		    return false;
		}

		CrateKey key = new CrateKey(p.getInventory().getItemInMainHand(), Boolean.parseBoolean(args[2]));
		crate.setKey(key);
		crateManager.save(crate);
		messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_SUCCESS,
			ImmutableMap.of("name", "" + crate.getName()));
		messageManager.crateNotification(crate, sender);
	    }

	    // add item command
	    if (args[0].equalsIgnoreCase("add"))
	    {
		if (args.length <= 5)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
		    return false;
		}
		if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_ADD.getKey()))
		{
		    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
		    return false;
		}
		if (p.getInventory().getItemInMainHand().getType() == Material.AIR)
		{
		    messageManager.sendMessage(sender, Message.MUST_HOLD_ITEM, null);
		    return false;
		}

		if (CommandUtils.tryParse(args[2]) == null || CommandUtils.tryParse(args[3]) == null
			|| CommandUtils.tryParse(args[4]) == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
		    return false;
		}
		Crate crate = crateManager.getCrateById(CommandUtils.tryParse(args[1]));
		if (crate == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
			    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
		    return false;
		}
		int min = CommandUtils.tryParse(args[2]);
		int max = CommandUtils.tryParse(args[3]);
		int chance = CommandUtils.tryParse(args[4]);

		if (min > max && min >= 1)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_MINMAX, null);
		    return false;
		}
		CrateItem item = new CrateItem(p.getInventory().getItemInMainHand(), min, max, chance,
			Boolean.parseBoolean(args[5]), null);
		crate.addItem(item);
		crateManager.save(crate);
		messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_SUCCESS,
			ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1]), "name", crate.getName(), "item",
				"" + item.getItem().getType(), "ItemId", "" + item.getId()));

		messageManager.crateNotification(crate, sender);
	    }

	    // remove item cmd
	    if (args[0].equalsIgnoreCase("remove"))
	    {
		if (args.length <= 2)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_REMOVE_USAGE, null);
		    return false;
		}
		if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_REMOVE.getKey()))
		{
		    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
		    return false;
		}
		if (CommandUtils.tryParse(args[1]) == null || CommandUtils.tryParse(args[2]) == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_REMOVE_USAGE, null);
		    return false;
		}
		Crate crate = crateManager.getCrateById(CommandUtils.tryParse(args[1]));
		if (crate == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
			    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
		    return false;
		}
		CrateItem item = crate.getItem(CommandUtils.tryParse(args[2]));
		if (item == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_ITEM_NOT_FOUND, ImmutableMap.of("id",
			    "" + CommandUtils.tryParse(args[1]), "ItemId", "" + CommandUtils.tryParse(args[2])));
		    return false;
		}
		crate.removeItem(item);
		crateManager.save(crate);
		messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_REMOVE_SUCCESS,
			ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1]), "name", crate.getName(), "ItemId",
				"" + CommandUtils.tryParse(args[2])));
		messageManager.crateNotification(crate, sender);
	    }

	    // items list cmd
	    if (args[0].equalsIgnoreCase("items"))
	    {
		if (args.length != 2)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_USAGE, null);
		    return false;
		}
		if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_ITEMS.getKey()))
		{
		    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
		    return false;
		}
		if (CommandUtils.tryParse(args[1]) == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_USAGE, null);
		    return false;
		}
		Crate crate = crateManager.getCrateById(CommandUtils.tryParse(args[1]));
		if (crate == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
			    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
		    return false;
		}
		List<CrateItem> items = crate.getItems();
		for (CrateItem item : items)
		{
		    Map<String, String> map = new HashMap<String, String>();
		    map.put("id", item.getId() + "");
		    map.put("type", item.getItem().getType() + "");
		    map.put("MinAmount", item.getMinAmount() + "");
		    map.put("MaxAmount", item.getMaxAmount() + "");
		    map.put("chance", item.getChance() + "");
		    map.put("name", item.getItem().getItemMeta().getDisplayName());
		    map.put("commands", item.getCommands().size() + "");
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_FORMAT,
			    ImmutableMap.copyOf(map));
		}
	    }

	    // set command
	    if (args[0].equalsIgnoreCase("set"))
	    {
		if (args.length != 2)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
		    return false;
		}
		if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_SET.getKey()))
		{
		    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
		    return false;
		}

		Location l = p.getTargetBlock(null, 10).getLocation();
		ImmutableMap<String, String> map1 = ImmutableMap.of("X", l.getBlockX() + "", "Y", l.getBlockY() + "",
			"Z", l.getBlockZ() + "");
		if (args[1].equalsIgnoreCase("none"))
		{
		    locationManager.removeCrateLocation(l);
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_REMOVE_SUCCESS, map1);
		    return false;
		}

		if (CommandUtils.tryParse(args[1]) == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
		    return false;
		}

		Crate crate = crateManager.getCrateById(CommandUtils.tryParse(args[1]));
		if (crate == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
			    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
		    return false;
		}
		ImmutableMap<String, String> map = ImmutableMap.of("id", crate.getId() + "", "name", crate.getName(),
			"X", l.getBlockX() + "", "Y", l.getBlockY() + "", "Z", l.getBlockZ() + "");

		locationManager.addCrateLocation(l, crate);
		messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_SUCCESS, map);

	    }

	    // get command
	    if (args[0].equalsIgnoreCase("get"))
	    {
		if (args.length < 2)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
		    return false;
		}
		if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_GET.getKey()))
		{
		    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
		    return false;
		}
		if (CommandUtils.tryParse(args[1]) == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
		    return false;
		}

		Crate crate = crateManager.getCrateById(CommandUtils.tryParse(args[1]));
		if (crate == null)
		{
		    messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
			    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
		    return false;
		}

		if (args.length == 3)
		{
		    if (CommandUtils.tryParse(args[2]) == null)
		    {
			messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
			return false;
		    }
		    for (int i = 0; i < CommandUtils.tryParse(args[2]); i++)
		    {
			p.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
		    }

		} else
		    p.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
		messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_SUCCESS,
			ImmutableMap.of("id", crate.getId() + "", "name", crate.getName()));

	    }

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

	//two arg commands
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
	    if(args.length == 3)
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
	
	//others
	if (args[0].equalsIgnoreCase("add"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    if(args.length == 3)
		list.add("[MinimumAmount]");
	    if(args.length == 4)
		list.add("[MaximumAmount]");
	    if(args.length == 5)
		list.add("[Chance]");
	    if(args.length == 6)
		list.add("[IsDisplay]");
	    return list;
	}
	if (args[0].equalsIgnoreCase("key"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    if(args.length == 3)
		list.add("[IsGlowing]");
	    return list;
	}
	if (args[0].equalsIgnoreCase("command"))
	{
	    if (args.length == 2)
		list.add("[CrateID]");
	    if(args.length == 3)
		list.add("[ItemID]");
	    if(args.length == 4)
		list.add("[Command] - Use {player} as placeholder");
	    return list;
	}
	return list;
    }

}
