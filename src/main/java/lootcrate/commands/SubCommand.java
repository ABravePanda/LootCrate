package lootcrate.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.other.Message;
import lootcrate.other.Permission;

public abstract class SubCommand {

    private LootCrate plugin;
    private CommandSender sender;
    private String[] args;
    private Permission[] permissions;
    
    public SubCommand(LootCrate plugin, CommandSender sender, String[] args, Permission...perm)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
	this.permissions = perm;
    }
   
    /**
     * Checks if sender has the given permissions
     * @param sender Sender to check
     * @param perm Permissions to check
     * @return True if all permissions pass, false if even one permission is missing
     */
    public boolean hasPermission(Permission... perm)
    {
	for(Permission p : perm)
	    if(!sender.hasPermission(p.getKey())) return false;
	return true;
    }
    
    /**
     * Tests if sender has the given permissions for command
     * @param sender Sender to check if they have permissions
     */
    public void testPermissions()
    {
	if (!hasPermission(permissions))
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
    }
    
    /**
     * Tests if a player is required to execute the command
     * @param playerRequired true if player is required, false if not
     * @return 
     */
    public boolean testPlayer(boolean playerRequired)
    {
	if(playerRequired && !(sender instanceof Player))
	{
	    plugin.messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
	    return true;
	}
	return false;
    }

    
    public abstract void runSubCommand(boolean playerRequired);
    public abstract List<String> runTabComplete();
    
}