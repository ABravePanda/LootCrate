package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;

public class SubCommandLootCrateCreate extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateCreate(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_CREATE, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if(this.testPlayer(playerRequired)) return;
	this.testPermissions();
	
	if (args.length <= 1)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_USAGE, null);
	    return;
	}

	Crate crate = new Crate(CommandUtils.builder(args, 1));
	plugin.crateManager.addDefaultOptions(crate);
	plugin.crateManager.save(crate);

	plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, "" + crate.getId()));
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();
	if (args.length == 2)
	    list.add("[CrateName]");
	return list;
    }

}
