package lootcrate.utils;

import org.bukkit.entity.Player;

import lootcrate.objects.Crate;
import lootcrate.other.Permission;
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

    public static double tryParseDouble(String text)
    {
	try
	{
	    return Double.parseDouble(text);
	} catch (NumberFormatException e)
	{
	    return 0.0;
	}
    }

    public static boolean hasCratePermission(Crate crate, Player p)
    {
	return p.hasPermission(Permission.LOOTCRATE_INTERACT.getKey() + crate.getId())
		|| p.hasPermission(Permission.LOOTCRATE_INTERACT_ADMIN.getKey());
    }

}
