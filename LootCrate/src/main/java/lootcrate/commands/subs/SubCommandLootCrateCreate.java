package lootcrate.commands.subs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.other.CrateOptionType;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateCreate implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateCreate(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand()
    {
	Player p = (Player) sender;

	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_CREATE.getKey())
		&& !p.hasPermission(Permission.LOOTCRATE_INTERACT_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (args.length <= 1)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_USAGE, null);
	    return;
	}

	Crate crate = new Crate(CommandUtils.builder(args, 1));

	String[] lines =
	{ "{crate_name}", "&8Right-Click&7 to Unlock", "&8Left-Click&7 to View" };

	crate.addOption(CrateOptionType.KNOCK_BACK, 1.0D);
	crate.addOption(CrateOptionType.DISPLAY_CHANCES, true);
	crate.addOption(CrateOptionType.OPEN_SOUND, Sound.UI_TOAST_CHALLENGE_COMPLETE.toString());
	crate.addOption(CrateOptionType.OPEN_MESSAGE, "&fYou have opened &e{crate_name}&f.");
	crate.addOption(CrateOptionType.HOLOGRAM_LINES, Arrays.asList(lines));
	crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_X, 0.5D);
	crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Y, 1.8D);
	crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Z, 0.5D);

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
