package lootcrate.commands.subs;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.gui.frames.menu.CrateMainMenuFrame;
import lootcrate.other.Permission;

public class SubCommandLootCrateGui extends SubCommand
{
    private String[] args;
    private CommandSender sender;
    private LootCrate plugin;

    public SubCommandLootCrateGui(LootCrate plugin, CommandSender sender, String[] args)
    {
	super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_ADD, Permission.COMMAND_LOOTCRATE_ADMIN);
	this.plugin = plugin;
	this.sender = sender;
	this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired)
    {
	if(this.testPlayer(playerRequired)) return;
	
	Player p = (Player) sender;

	if(!this.testPermissions()) return;

	CrateMainMenuFrame frame = new CrateMainMenuFrame(plugin, p);
	frame.open();
	
    }

    @Override
    public List<String> runTabComplete()
    {
	List<String> list = new LinkedList<String>();

	return list;
    }

}
