package com.tompkins_development.spigot.lootcrate.managers;

import java.util.Map;
import java.util.regex.Matcher;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;
import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.enums.Message;
import com.tompkins_development.spigot.lootcrate.enums.Placeholder;
import com.tompkins_development.spigot.lootcrate.objects.Crate;

public class MessageManager
{
    private LootCrate plugin;
    private final String PREFIX = "messages.";

    /**
     * Constructor of MessageManager
     * 
     * @param plugin
     *            Instance of plugin
     */
    public MessageManager(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    /**
     * Sends a message to specified player
     * 
     * @param p
     *            Player to whom shall recieve the message
     * @param message
     *            Message to be sent
     * @param placeholders
     *            Placeholders to be replaced in message
     */
    public void sendMessage(CommandSender p, Message message, ImmutableMap<Placeholder, String> placeholders)
    {
	String msg = this.parseMessage(message, placeholders);
	if (msg != null)
	    p.sendMessage(this.parseMessage(Message.PREFIX, null) + msg);
    }

    /**
     * Sends a message to specified player without the prefix
     * 
     * @param p
     *            Player to whom shall recieve the message
     * @param message
     *            Message to be sent
     * @param placeholders
     *            Placeholders to be replaced in message
     */
    public void sendNoPrefixMessage(CommandSender p, Message message, ImmutableMap<Placeholder, String> placeholders)
    {
	String msg = this.parseMessage(message, placeholders);
	if (msg != null)
	    p.sendMessage(msg);
    }

    /**
     * Converts all placeholders in message
     * 
     * @param message
     *            Message to be fixed
     * @param placeholders
     *            Placeholders to be replaced in message
     * @return Message with placeholders replaced
     */
    public String parseMessage(Message message, ImmutableMap<Placeholder, String> placeholders)
    {
	String msg = plugin.getConfig().getString(PREFIX + message.getKey());

	if (msg == null || msg.isEmpty())
	    return null;

	String colorMsg = ChatColor.translateAlternateColorCodes('&', msg);

	if (placeholders != null)
	{
	    for (Map.Entry<Placeholder, String> entry : placeholders.entrySet())
	    {
		colorMsg = colorMsg.replaceAll("\\{" + entry.getKey().getKey() + "\\}",
			Matcher.quoteReplacement(entry.getValue()));
	    }
	}
	return colorMsg;
    }

    public String getPrefix()
    {
	return this.parseMessage(Message.PREFIX, null);
    }

    /**
     * Sends a notification that crate is not complete
     * 
     * @param crate
     *            Referenced crate
     * @param sender
     *            Whom shall receive the message
     */
    @Deprecated
    public void crateNotification(Crate crate, CommandSender sender)
    {
	if (crate.getChanceCount() != 100)
	{
	    this.sendMessage(sender, Message.LOOTCRATE_CHANCE_NOT_100,
		    ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
			    Placeholder.TOTAL_CRATE_CHANCE, "" + crate.getChanceCount()));
	}
    }
}
