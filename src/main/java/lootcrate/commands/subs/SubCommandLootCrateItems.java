package lootcrate.commands.subs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

public class SubCommandLootCrateItems implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    
    public SubCommandLootCrateItems(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand()
    {
	Player p = (Player) sender;
	
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_ITEMS.getKey()) && !p.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (args.length != 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_USAGE, null);
	    return;
	}
	if (CommandUtils.tryParse(args[1]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_USAGE, null);
	    return;
	}
	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	List<CrateItem> items = crate.getItems();
	for (CrateItem item : items)
	{
	    Map<Placeholder, String> map = new HashMap<Placeholder, String>();
	    map.put(Placeholder.ITEM_ID, item.getId() + "");
	    map.put(Placeholder.ITEM_TYPE, item.getItem().getType() + "");
	    map.put(Placeholder.ITEM_MIN_AMOUNT, item.getMinAmount() + "");
	    map.put(Placeholder.ITEM_MAX_AMOUNT, item.getMaxAmount() + "");
	    map.put(Placeholder.ITEM_CHANCE, item.getChance() + "");
	    map.put(Placeholder.ITEM_NAME, item.getItem().getItemMeta().getDisplayName().length() == 0 ? "None" : item.getItem().getItemMeta().getDisplayName());
	    map.put(Placeholder.ITEM_COMMANDS, item.getCommands().size() + "");
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_FORMAT,
		    ImmutableMap.copyOf(map));
	}
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();
	
	Player p = (Player) sender;
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_ITEMS.getKey())
		&& !p.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;
	
	if (args.length == 2)
	    {
		list.add("[CrateID]");
		TabUtils.addCratesToList(list, plugin.crateManager);
	    }
	    return list;
    }
    
    
}
