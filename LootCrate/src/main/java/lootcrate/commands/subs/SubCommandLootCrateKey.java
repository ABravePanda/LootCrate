package lootcrate.commands.subs;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateKey;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateKey implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    
    public SubCommandLootCrateKey(LootCrate plugin, CommandSender sender, String[] args)
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
	
	if (args.length <= 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_USAGE, null);
	    return;
	}
	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_KEY.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (p.getInventory().getItemInMainHand().getType() == Material.AIR)
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_HOLD_ITEM, null);
	    return;
	}
	if (CommandUtils.tryParse(args[1]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_USAGE, null);
	    return;
	}
	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}

	CrateKey key = new CrateKey(p.getInventory().getItemInMainHand(), Boolean.parseBoolean(args[2]));
	crate.setKey(key);
	plugin.crateManager.save(crate);
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_NAME, "" + crate.getName(), Placeholder.CRATE_ID, "" + crate.getId()));
	plugin.messageManager.crateNotification(crate, sender);
    }
    
    
}
