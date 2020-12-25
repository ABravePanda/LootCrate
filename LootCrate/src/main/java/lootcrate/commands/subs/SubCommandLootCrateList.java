package lootcrate.commands.subs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.CrateKey;
import lootcrate.other.Message;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import lootcrate.utils.interfaces.SubCommand;

public class SubCommandLootCrateList implements SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateList(LootCrate plugin, CommandSender sender, String[] args)
    {
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand()
    {
	Player p = (Player) sender;

	if (!p.hasPermission(Permission.COMMAND_LOOTCRATE_LIST.getKey())
		&& !p.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
	{
	    plugin.messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
	    return;
	}
	if (args.length != 1)
	{
	    plugin.messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_LIST_USAGE, null);
	    return;
	}

	List<Crate> crates = plugin.crateManager.load();
	for (Crate crate : crates)
	{
	    Map<Placeholder, String> map = new HashMap<Placeholder, String>();
	    map.put(Placeholder.CRATE_ID, crate.getId() + "");
	    map.put(Placeholder.CRATE_NAME, crate.getName());
	    map.put(Placeholder.CRATE_ITEM_COUNT, crate.getItems().size() + "");
	    map.put(Placeholder.CRATE_KEY_TYPE, crate.getKey() == null ? "None" : crate.getKey().getItem().getType() + "");
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
