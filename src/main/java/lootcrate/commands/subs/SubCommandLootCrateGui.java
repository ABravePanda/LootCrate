package lootcrate.commands.subs;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Permission;
import lootcrate.gui.menu.AnimationType;
import lootcrate.gui.menu.MenuType;
import lootcrate.gui.menu.SimpleMenu;
import lootcrate.gui.menu.TestMenu;
import lootcrate.gui.menu.creation.MenuCreationSize;
import lootcrate.managers.MenuManager;
import lootcrate.utils.TabUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateGui extends SubCommand {
    private final String[] args;
    private final CommandSender sender;
    private final LootCrate plugin;
    private final MenuManager menuManager;

    /**
     * Default constructor for any {@link lootcrate.commands.SubCommand}
     *
     * @param plugin an instance of {@link lootcrate.LootCrate}
     * @param sender the {@link org.bukkit.command.CommandSender} which is executing this command
     * @param args the following arguments in the command string
     *
     */
    public SubCommandLootCrateGui(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_GUI, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        this.menuManager = plugin.getManager(MenuManager.class);
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;

        Player p = (Player) sender;

        if (!this.testPermissions())
            return;

        if(args[1].equalsIgnoreCase("create")) {
            menuManager.beginMenuCreationStage(p, 1);
        }

    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        if (args.length == 2) {
            list.add("create");
            list.add("edit");
            list.add("settings");
        }

        if(args.length == 3) {
            if(args[1].equalsIgnoreCase("create")) {
                TabUtils.addCratesToList(list, cacheManager);
            }
        }

        return list;
    }

}
