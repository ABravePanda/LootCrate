package com.tompkins_development.spigot.lootcrate.managers;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.commands.LootCrateCommand;
import com.tompkins_development.spigot.lootcrate.commands.MessageCommand;
import com.tompkins_development.spigot.lootcrate.commands.MetaCommand;

public class CommandManager implements CommandExecutor, TabCompleter
{
    private LootCrate plugin;
    private MessageManager messageManager;
    private CrateManager crateManager;
    private LocationManager locationManager;

    public CommandManager(LootCrate plugin)
    {
	this.plugin = plugin;
	this.messageManager = plugin.getMessageManager();
	this.crateManager = plugin.getCrateManager();
	this.locationManager = plugin.getLocationManager();

	plugin.getCommand("message").setExecutor(this);
	plugin.getCommand("message").setTabCompleter(this);
	plugin.getCommand("lootcrate").setExecutor(this);
	plugin.getCommand("lootcrate").setTabCompleter(this);
	plugin.getCommand("meta").setExecutor(this);
	plugin.getCommand("meta").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

	if (cmd.getName().equalsIgnoreCase("message"))
	    new MessageCommand(plugin, args, sender).executeCommand();
	if (cmd.getName().equalsIgnoreCase("lootcrate"))
	    new LootCrateCommand(plugin, args, sender).executeCommand();
	if (cmd.getName().equalsIgnoreCase("meta"))
	    new MetaCommand(plugin, args, sender).executeCommand();
	return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
    {
	if (cmd.getName().equalsIgnoreCase("message"))
	    return new MessageCommand(plugin, args, sender).runTabComplete();
	if (cmd.getName().equalsIgnoreCase("lootcrate"))
	    return new LootCrateCommand(plugin, args, sender).runTabComplete();
	if (cmd.getName().equalsIgnoreCase("meta"))
	    return new MetaCommand(plugin, args, sender).runTabComplete();
	return null;
    }
}