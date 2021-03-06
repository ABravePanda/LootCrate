package lootcrate.commands.subs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;

public class SubCommandLootCrateList extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateList(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_LIST, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if(this.testPlayer(playerRequired)) return;
	this.testPermissions();
	
	if (args.length != 1)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_LIST_USAGE, null);
	    return;
	}

	List<Crate> crates = plugin.cacheManager.getCache();
	for (Crate crate : crates)
	{
	    Map<Placeholder, String> map = new HashMap<Placeholder, String>();
	    map.put(Placeholder.CRATE_ID, crate.getId() + "");
	    map.put(Placeholder.CRATE_NAME, crate.getName());
	    map.put(Placeholder.CRATE_ITEM_COUNT, crate.getItems().size() + "");
	    map.put(Placeholder.CRATE_KEY_TYPE, crate.getKey().getItem() == null ? "None" : crate.getKey().getItem().getType() + "");
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_LIST_FORMAT, ImmutableMap.copyOf(map));
	}
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	return list;
    }

}
