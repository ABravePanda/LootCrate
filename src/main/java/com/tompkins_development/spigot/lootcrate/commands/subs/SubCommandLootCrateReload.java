package com.tompkins_development.spigot.lootcrate.commands.subs;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.commands.SubCommand;
import com.tompkins_development.spigot.lootcrate.enums.Message;
import com.tompkins_development.spigot.lootcrate.enums.Permission;

public class SubCommandLootCrateReload extends SubCommand
{
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateReload(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_RELOAD, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if (this.testPlayer(playerRequired))
	    return;
	if (!this.testPermissions())
	    return;

	plugin.reloadConfig();
	plugin.reload();

	plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_RELOAD_SUCCESS, null);
    }

    @Override
    public List<String> runTabComplete()
    {
	return null;
    }

}
