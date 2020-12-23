package lootcrate.commands.subs;

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

public class SubCommandLootCrateAdd implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    
    public SubCommandLootCrateAdd(LootCrate plugin, CommandSender sender, String[] args)
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
	
	if (args.length <= 5)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
	    return;
	}
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_ADD.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (p.getInventory().getItemInMainHand().getType() == Material.AIR)
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_HOLD_ITEM, null);
	    return;
	}

	if (CommandUtils.tryParse(args[2]) == null || CommandUtils.tryParse(args[3]) == null
		|| CommandUtils.tryParse(args[4]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
	    return;
	}
	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	int min = CommandUtils.tryParse(args[2]);
	int max = CommandUtils.tryParse(args[3]);
	int chance = CommandUtils.tryParse(args[4]);

	if (min > max && min >= 1)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_MINMAX, null);
	    return;
	}
	CrateItem item = new CrateItem(p.getInventory().getItemInMainHand(), min, max, chance,
		Boolean.parseBoolean(args[5]), null);
	crate.addItem(item);
	plugin.crateManager.save(crate);
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(), Placeholder.ITEM_TYPE,
			"" + item.getItem().getType(), Placeholder.ITEM_ID, "" + item.getId(), Placeholder.ITEM_NAME, "" + item.getItem().getItemMeta().getDisplayName()));

	plugin.messageManager.crateNotification(crate, sender);
    }
    
    
}
