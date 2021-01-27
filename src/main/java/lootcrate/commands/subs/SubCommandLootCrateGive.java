package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.ObjUtils;
import lootcrate.utils.TabUtils;

public class SubCommandLootCrateGive extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateGive(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_GIVE, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if(this.testPlayer(playerRequired)) return;
	this.testPermissions();

	if (args.length < 3)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
	    return;
	}
	if (Bukkit.getPlayer(args[1]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.PLAYER_NOT_FOUND,
		    ImmutableMap.of(Placeholder.PLAYER_NAME, args[1]));
	    return;
	}

	Player player = Bukkit.getPlayer(args[1]);

	if (CommandUtils.tryParse(args[2]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
	    return;
	}

	Crate crate = plugin.crateManager.getCrateById(CommandUtils.tryParse(args[2]));
	if (crate == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[2])));
	    return;
	}

	if (crate.getKey() == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.KEY_NOT_FOUND, ImmutableMap.of(Placeholder.CRATE_ID,
		    "" + CommandUtils.tryParse(args[2]), Placeholder.CRATE_NAME, "" + crate.getName()));
	    return;
	}

	if (args.length == 4)
	{
	    if (CommandUtils.tryParse(args[3]) == null)
	    {
		plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GET_USAGE, null);
		return;
	    }
	    for (int i = 0; i < CommandUtils.tryParse(args[3]); i++)
	    {
		player.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
	    }

	} else
	    player.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_GIVE_SUCCESS_SENDER,
		ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName(),
			Placeholder.PLAYER_NAME, player.getName()));
	plugin.messageManager.sendMessage(player, Message.LOOTCRATE_COMMAND_GIVE_SUCCESS_RECEIVER,
		ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName(),
			Placeholder.SENDER_NAME, sender.getName()));

    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_GIVE.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;

	if (args.length == 2)
	{
	    for (Player pl : Bukkit.getOnlinePlayers())
		list.add(pl.getName());
	}
	if (args.length == 3)
	{
	    list.add("[CrateID]");
	    TabUtils.addCratesToList(list, plugin.crateManager);
	}
	if (args.length == 4)
	    list.add("(Amount)");
	return list;
    }

}
