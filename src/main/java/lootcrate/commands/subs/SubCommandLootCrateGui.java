package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.gui.frames.menu.CrateFrame;
import lootcrate.gui.frames.menu.CrateMainMenuFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;

public class SubCommandLootCrateGui extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateGui(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_GUI, Permission.COMMAND_LOOTCRATE_ADMIN);
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

	Frame frame = new CrateMainMenuFrame(plugin, p);
	if (args.length == 2)
	{
	    Crate crate = plugin.cacheManager.getCrateById(CommandUtils.tryParse(args[1]));
	    if (crate == null)
	    {
		plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
			ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
		return;
	    }
	    frame = new CrateFrame(plugin, p, crate);
	}

	frame.open();

    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	if (args.length == 2)
	{
	    list.add("[CrateID]");
	    TabUtils.addCratesToList(list, plugin.cacheManager);
	}

	return list;
    }

}
