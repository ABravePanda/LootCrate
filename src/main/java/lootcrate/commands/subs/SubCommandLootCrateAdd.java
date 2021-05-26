package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;

public class SubCommandLootCrateAdd extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateAdd(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_ADD, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if (this.testPlayer(playerRequired))
	    return;

	Player p = (Player) sender;

	if (!this.testPermissions())
	    return;

	if (args.length <= 5)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
	    return;
	}
	if (p.getInventory().getItemInMainHand().getType() == Material.AIR)
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_HOLD_ITEM, null);
	    return;
	}

	if (CommandUtils.tryParse(args[2]) == null || CommandUtils.tryParse(args[3]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
	    return;
	}
	Crate crate = plugin.cacheManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	int min = CommandUtils.tryParse(args[2]);
	int max = CommandUtils.tryParse(args[3]);
	double chance = CommandUtils.tryParseDouble(args[4]);

	if (min > max && min >= 1)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_MINMAX, null);
	    return;
	}
	CrateItem item = new CrateItem(p.getInventory().getItemInMainHand(), min, max, chance,
		Boolean.parseBoolean(args[5]), null);
	crate.addItem(item);
	plugin.cacheManager.update(crate);
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
			Placeholder.ITEM_TYPE, "" + item.getItem().getType(), Placeholder.ITEM_ID, "" + item.getId(),
			Placeholder.ITEM_NAME, "" + item.getItem().getItemMeta().getDisplayName()));

    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADD.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;

	if (args.length == 2)
	{
	    list.add("[CrateID]");
	    TabUtils.addCratesToList(list, plugin.cacheManager);
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

}
