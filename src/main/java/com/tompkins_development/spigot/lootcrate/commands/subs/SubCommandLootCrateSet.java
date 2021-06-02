package com.tompkins_development.spigot.lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;
import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.commands.SubCommand;
import com.tompkins_development.spigot.lootcrate.enums.Message;
import com.tompkins_development.spigot.lootcrate.enums.Permission;
import com.tompkins_development.spigot.lootcrate.enums.Placeholder;
import com.tompkins_development.spigot.lootcrate.managers.HologramManager;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.utils.CommandUtils;
import com.tompkins_development.spigot.lootcrate.utils.TabUtils;

public class SubCommandLootCrateSet extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    private HologramManager holoManager;

    public SubCommandLootCrateSet(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_SET, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
	this.holoManager = plugin.getHoloManager();
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if (this.testPlayer(playerRequired))
	    return;

	Player p = (Player) sender;

	if (!this.testPermissions())
	    return;

	if (args.length != 2)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
	    return;
	}

	Location l = p.getTargetBlock(null, 10).getLocation();
	ImmutableMap<Placeholder, String> map1 = ImmutableMap.of(Placeholder.X, l.getBlockX() + "", Placeholder.Y,
		l.getBlockY() + "", Placeholder.Z, l.getBlockZ() + "");
	if (args[1].equalsIgnoreCase("none"))
	{
	    plugin.getLocationManager().removeCrateLocation(l);
	    if (plugin.holoHook())
		plugin.getHoloManager().reload();
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_REMOVE_SUCCESS, map1);
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
	ImmutableMap<Placeholder, String> map = ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "",
		Placeholder.CRATE_NAME, crate.getName(), Placeholder.X, l.getBlockX() + "", Placeholder.Y,
		l.getBlockY() + "", Placeholder.Z, l.getBlockZ() + "");

	plugin.getLocationManager().addCrateLocation(l, crate);

	// create hologram
	if (plugin.holoHook())
	    holoManager.createHologram(l.getBlock(), crate);

	plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_SUCCESS, map);
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_SET.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;

	if (args.length == 2)
	{
	    list.add("[CrateID]");
	    list.add("none");
	    TabUtils.addCratesToList(list, plugin.getCacheManager());
	}
	return list;
    }

}
