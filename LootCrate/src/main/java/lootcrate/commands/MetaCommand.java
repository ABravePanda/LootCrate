package lootcrate.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lootcrate.LootCrate;
import lootcrate.managers.MessageManager;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;

public class MetaCommand implements CommandExecutor
{

    private LootCrate plugin;
    private MessageManager messageManager;

    public MetaCommand(LootCrate plugin)
    {
	this.plugin = plugin;
	this.messageManager = plugin.messageManager;

	plugin.getCommand("meta").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	// sender must be player
	if (!(sender instanceof Player))
	{
	    messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
	    return false;
	}

	Player p = (Player) sender;

	// player needs to have permission
	if (!p.hasPermission(Permission.COMMAND_META.getKey()))
	{
	    messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return false;
	}
	
	if(p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR)
	{
	    messageManager.sendMessage(sender, Message.MUST_HOLD_ITEM, null);
	    return false;
	}
	
	ItemStack item = p.getInventory().getItemInMainHand();

	// need right amount of args
	if (args.length > 1)
	{
	    if (args[0].equalsIgnoreCase("name"))
	    {
		p.getInventory().setItemInMainHand(ItemUtils.setDisplayName(item, CommandUtils.builder(args,1)));		
	    } else if (args[0].equalsIgnoreCase("lore"))
	    {
		p.getInventory().setItemInMainHand(ItemUtils.setLore(item, CommandUtils.builder(args,1).split("\\|")));		
	    } else if (args[0].equalsIgnoreCase("enchantment"))
	    {

	    } else
		messageManager.sendMessage(sender, Message.META_USAGE, null);
	} else
	    messageManager.sendMessage(sender, Message.META_USAGE, null);

	return false;
    }


}
