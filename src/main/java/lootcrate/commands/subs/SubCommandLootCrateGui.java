package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.gui.Frame;
import lootcrate.gui.GUIItem;
import lootcrate.gui.ItemBuilder;
import lootcrate.gui.frames.TestFrame;
import lootcrate.managers.FrameManager;
import lootcrate.objects.Crate;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateGui extends SubCommand {
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
    public SubCommandLootCrateGui(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_GUI, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;

        Player p = (Player) sender;

        if (!this.testPermissions())
            return;


        FrameManager frameManager = plugin.getFrameManager();
        Frame frame = new TestFrame(plugin, 9, "Test", p.getUniqueId());

        frameManager.openFrame(frame, p.getUniqueId());

        GUIItem guiItem = new ItemBuilder(plugin, Material.STONE).setDurability(22).setName("Test").getClickableItem((itemStack, uuid) -> {
            System.out.println("Test click");
        });
        frame.setItem(0, guiItem);

    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        if (args.length == 2) {
            list.add("[CrateID]");
            TabUtils.addCratesNamesToList(list, plugin.getCacheManager());
        }

        return list;
    }

}
