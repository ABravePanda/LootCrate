package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.objects.Crate;
import lootcrate.objects.Display;
import lootcrate.utils.CommandUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateCreate extends SubCommand {
    private final String[] args;
    private final CommandSender sender;
    private final LootCrate plugin;


    /**
     * Default constructor for any {@link lootcrate.commands.SubCommand}
     *
     * @param plugin an instance of {@link lootcrate.LootCrate}
     * @param sender the {@link org.bukkit.command.CommandSender} which is executing this command
     * @param args the following arguments in the command string
     *
     */
    public SubCommandLootCrateCreate(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_CREATE, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    /**
     * Is called when the {@link lootcrate.commands.SubCommand} class calls upon it.
     * This SubCommand is used for the configuration of adding commands to a {@link lootcrate.objects.CrateItem } It guarantees all arguments
     * must be valid. It adds the command, replaces the old CrateItem, then updates the cache
     *
     * @param playerRequired if a player is required to run this command can any {@link org.bukkit.command.CommandSender}
     *
     */
    @Override
    public void runSubCommand(boolean playerRequired) {

        if (this.testPlayer(playerRequired))
            return;

        if (!this.testPermissions())
            return;

        if (args.length <= 1) {
            plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_USAGE, null);
            return;
        }

        Crate crate = new Crate(CommandUtils.builder(args, 1));
        crate.setDisplay(createDisplay());
        plugin.getCrateManager().addDefaultOptions(crate);
        plugin.getCacheManager().update(crate);

        plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_SUCCESS,
                ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, "" + crate.getId()));
        plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_CREATE_PERMISSIONS,
                ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, "" + crate.getId()));
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();
        if (args.length == 2)
            list.add("[CrateName]");
        return list;
    }

    //DEBUG

    private Display createDisplay()
    {
        Display display = new Display(45);
        List<Integer> line1 = Arrays.asList(new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        List<Integer> line2 = Arrays.asList(new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        List<Integer> line3 = Arrays.asList(new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        List<Integer> line4 = Arrays.asList(new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        List<Integer> line5 = Arrays.asList(new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        List<List<Integer>> page = new ArrayList<>();
        page.add(line1);
        page.add(line2);
        page.add(line3);
        page.add(line4);
        page.add(line5);
        System.out.println(display.addPage(page));
        display.addItem(0, new ItemStack(Material.AIR));
        return display;
    }

}
