package lootcrate.utils;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

import lootcrate.managers.MessageManager;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import net.md_5.bungee.api.ChatColor;

public class CommandUtils
{
    public static String builder(String[] args, int startingIndex)
    {
	if (startingIndex > args.length)
	    return "";

	String finalString = "";
	int index = 0;

	for (String s : args)
	{
	    if (startingIndex > index)
	    {
		index++;
		continue;
	    }
	    if (index != args.length - 1)
		finalString += s + " ";
	    else
		finalString += s;
	    index++;
	}
	return ChatColor.translateAlternateColorCodes('&', finalString);
    }

    public static Integer tryParse(String text)
    {
	try
	{
	    return Integer.parseInt(text);
	} catch (NumberFormatException e)
	{
	    return null;
	}
    }
    

}
