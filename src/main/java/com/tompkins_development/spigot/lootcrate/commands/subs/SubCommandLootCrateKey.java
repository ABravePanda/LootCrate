package com.tompkins_development.spigot.lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;
import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.commands.SubCommand;
import com.tompkins_development.spigot.lootcrate.enums.Message;
import com.tompkins_development.spigot.lootcrate.enums.Permission;
import com.tompkins_development.spigot.lootcrate.enums.Placeholder;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.objects.CrateKey;
import com.tompkins_development.spigot.lootcrate.utils.CommandUtils;
import com.tompkins_development.spigot.lootcrate.utils.ObjUtils;
import com.tompkins_development.spigot.lootcrate.utils.TabUtils;

public class SubCommandLootCrateKey extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateKey(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_KEY, Permission.COMMAND_LOOTCRATE_ADMIN);
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

	if (args.length <= 2)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_USAGE, null);
	    return;
	}
	if (p.getInventory().getItemInMainHand().getType() == Material.AIR)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.MUST_HOLD_ITEM, null);
	    return;
	}
	if (CommandUtils.tryParse(args[1]) == null)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_USAGE, null);
	    return;
	}
	Crate crate = plugin.getCacheManager().getCrateById(CommandUtils.tryParse(args[1]));
	if (crate == null)
	{
	    plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
	    return;
	}

	CrateKey key = new CrateKey(p.getInventory().getItemInMainHand(), Boolean.parseBoolean(args[2]));
	crate.setKey(key);
	plugin.getCacheManager().update(crate);
	plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_KEY_SUCCESS, ImmutableMap
		.of(Placeholder.CRATE_NAME, "" + crate.getName(), Placeholder.CRATE_ID, "" + crate.getId()));
	p.getInventory().setItemInMainHand(ObjUtils.assignCrateToItem(plugin, crate));
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_KEY.getKey())
		&& !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	    return list;

	if (args.length == 2)
	{
	    list.add("[CrateID]");
	    TabUtils.addCratesToList(list, plugin.getCacheManager());
	}
	if (args.length == 3)
	{
	    list.add("[Is Glowing]");
	    list.add("true");
	    list.add("false");
	}
	return list;
    }

}
