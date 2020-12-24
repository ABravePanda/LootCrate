package lootcrate.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Option;
import lootcrate.other.Placeholder;

public class OptionManager
{
    private LootCrate plugin;
    private final String PREFIX = "options.";

    /**
     * Constructor of OptionManager
     * 
     * @param plugin
     *            Instance of plugin
     */
    public OptionManager(LootCrate plugin)
    {
	this.plugin = plugin;
    }


    public <T> T valueOf(Option option)
    {
	switch (option.getType())
	{
	case INTEGER:
	    return (T) (Integer) plugin.getConfig().getInt(PREFIX + option.getKey());
	case BOOLEAN:
	    return (T) (Boolean) plugin.getConfig().getBoolean(PREFIX + option.getKey());
	case DOUBLE:
	    return (T) (Double) plugin.getConfig().getDouble(PREFIX + option.getKey());
	case LIST:
	    return (T) (List) plugin.getConfig().getList(PREFIX + option.getKey());
	case STRING:
	    return (T) (String) plugin.getConfig().getString(PREFIX + option.getKey());
	default:
	    return null;

	}
    }

}
