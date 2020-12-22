package lootcrate.managers;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.other.Message;

public class MessageManager
{
    private LootCrate plugin;
    private final String PREFIX = "messages.";

    public MessageManager(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    public void sendMessage(CommandSender p, Message message, ImmutableMap<String, String> placeholders)
    {
	String msg = this.parseMessage(message, placeholders);
	if (msg != null)
	    p.sendMessage(this.parseMessage(Message.PREFIX, null) + msg);
    }

    public String parseMessage(Message message, ImmutableMap<String, String> placeholders)
    {
	String msg = plugin.getConfig().getString(PREFIX + message.getKey());

	if (msg == null || msg.isEmpty())
	    return null;

	String colorMsg = ChatColor.translateAlternateColorCodes('&', msg);

	if (placeholders != null)
	{
	    for (Map.Entry<String, String> entry : placeholders.entrySet())
	    {
		colorMsg = colorMsg.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
	    }
	}
	return colorMsg;
    }
    
    public void crateNotification(Crate crate, CommandSender sender)
    {
	if(crate.getChanceCount() != 100)
	{
	    this.sendMessage(sender, Message.LOOTCRATE_CHANCE_NOT_100, ImmutableMap.of("id", "" + crate.getId(), "name", crate.getName(), "ChanceTotal", "" + crate.getChanceCount()));
	}
    }
}
