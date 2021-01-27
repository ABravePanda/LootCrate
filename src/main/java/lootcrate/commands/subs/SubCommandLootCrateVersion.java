package lootcrate.commands.subs;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.managers.UpdateManager;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.utils.interfaces.SubCommand;
import net.md_5.bungee.api.ChatColor;

public class SubCommandLootCrateVersion implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;
    private UpdateManager updateManager;
    
    public SubCommandLootCrateVersion(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
	this.updateManager = plugin.updateManager;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if(playerRequired && !(sender instanceof Player))
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
	    return;
	}
	
	if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_VERSION.getKey()) && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (args.length != 1)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_VERION_USAGE, null);
	    return;
	}
	
	sender.sendMessage(ChatColor.YELLOW + "Running " + plugin.getName() + " v" + plugin.getDescription().getVersion());
	if(updateManager.checkForUpdates())
	{
	    sender.sendMessage(ChatColor.RED + "New update found! (v" + updateManager.getNewVersion() + ")." );
	    sender.sendMessage(ChatColor.RED + "Download @ " + updateManager.getResourceURL());
	}
	
	
    }

    @Override
    public List<String> runTabComplete()
    {
	return null;
    }
    
    
}
