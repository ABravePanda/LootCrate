package com.tompkins_development.spigot.lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;
import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.commands.SubCommand;
import com.tompkins_development.spigot.lootcrate.enums.Message;
import com.tompkins_development.spigot.lootcrate.enums.Permission;
import com.tompkins_development.spigot.lootcrate.enums.Placeholder;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.utils.CommandUtils;
import com.tompkins_development.spigot.lootcrate.utils.TabUtils;

public class SubCommandLootCrateDelete extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateDelete(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_DELETE, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if (this.testPlayer(playerRequired))
	    return;
	if (!this.testPermissions())
	    return;

	if (args.length <= 1)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_DELETE_USAGE, null);
	    return;
	}
	if (CommandUtils.tryParse(args[1]) == null)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
	    return;
	}

	Crate crate = plugin.getCacheManager().getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}
	plugin.getLocationManager().removeCrateLocation(crate);
	plugin.getCacheManager().remove(crate);
	plugin.getCacheManager().save();
	plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_DELETE_SUCCESS,
		ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, "" + crate.getId()));
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_DELETE.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;

	if (args.length == 2)
	{
	    list.add("[CrateID]");
	    TabUtils.addCratesToList(list, plugin.getCacheManager());
	}
	return list;
    }

}
