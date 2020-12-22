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
		    ImmutableMap.of("id", "" + CommandUtils.tryParse(args[1])));
	    return;
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
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_FORMAT,
		    ImmutableMap.copyOf(map));
	}
    }
    
    
}
