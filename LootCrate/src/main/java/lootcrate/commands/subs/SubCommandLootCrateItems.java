package lootcrate.commands.subs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	runSubCommand();
    }

    @Override
    public void runSubCommand()
    {
	Player p = (Player) sender;
	
	if (args.length != 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_USAGE, null);
	    return;
	}
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_ITEMS.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
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
	    map.put(Placeholder.ITEM_NAME, item.getItem().getItemMeta().getDisplayName());
	    map.put(Placeholder.ITEM_COMMANDS, item.getCommands().size() + "");
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_FORMAT,
		    ImmutableMap.copyOf(map));
	}
    }
    
    
}
