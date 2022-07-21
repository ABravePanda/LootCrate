package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.gui.frames.menu.CrateMainMenuFrame;
import lootcrate.gui.frames.menu.CrateFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.objects.Crate;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
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

        Frame frame = new CrateMainMenuFrame(plugin, p);
        if (args.length == 2) {
            Crate crate = plugin.getCacheManager().getCrateById(CommandUtils.tryParse(args[1]));
            if (crate == null) {
                plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
                        ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
                return;
            }
            frame = new CrateFrame(plugin, p, crate);
        }

        plugin.getInvManager().openFrame(p, frame);

    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        if (args.length == 2) {
            list.add("[CrateID]");
            TabUtils.addCratesToList(list, plugin.getCacheManager());
        }

        return list;
    }

}
