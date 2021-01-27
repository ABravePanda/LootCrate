package lootcrate.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.other.Message;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;

public class MessageCommand extends Command
{

    private LootCrate plugin;
    private String[] args;
    private CommandSender sender;

    public MessageCommand(LootCrate plugin, String[] args, CommandSender sender)
    {
	this.plugin = plugin;
	this.args = args;
	this.sender = sender;
    }

    @Override
    public void executeCommand()
    {
	if (sender instanceof Player)
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_BE_CONSOLE, null);
	    return;
	}

	if (args.length < 2)
	{
	    plugin.messageManager.sendMessage(sender, Message.MESSAGE_COMMAND_USAGE, null);
	    return;
	}

	if (Bukkit.getPlayer(args[0]) == null)
	{
	    plugin.messageManager.sendMessage(sender, Message.PLAYER_NOT_FOUND, ImmutableMap.of(Placeholder.PLAYER_NAME, args[0]));
	    return;
	}

	Player p = Bukkit.getPlayer(args[0]);

	plugin.messageManager.sendNoPrefixMessage(p, Message.MESSAGE_COMMAND_FORMAT,
		ImmutableMap.of(Placeholder.PLAYER, args[0], Placeholder.MESSAGE, CommandUtils.builder(args, 1)));
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();
	if (args.length == 1)
	{
	    list.add("[Player]");
	    for (Player p : Bukkit.getOnlinePlayers())
	    {
		list.add(p.getName());
	    }
	    return list;
	}
	if(args.length == 2)
	    list.add("[Message]");
	return list;
    }

}
