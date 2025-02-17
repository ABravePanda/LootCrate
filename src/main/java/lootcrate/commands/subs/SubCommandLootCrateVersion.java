package lootcrate.commands.subs;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.managers.UpdateManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SubCommandLootCrateVersion extends SubCommand {
    private final String[] args;
    private final CommandSender sender;
    private final LootCrate plugin;
    private final UpdateManager updateManager;

    /**
     * Default constructor for any {@link lootcrate.commands.SubCommand}
     *
     * @param plugin an instance of {@link lootcrate.LootCrate}
     * @param sender the {@link org.bukkit.command.CommandSender} which is executing this command
     * @param args the following arguments in the command string
     *
     */
    public SubCommandLootCrateVersion(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_VERSION, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        this.updateManager = plugin.getManager(UpdateManager.class);
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;
        if (!this.testPermissions())
            return;

        if (args.length != 1) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_VERION_USAGE, null);
            return;
        }

        updateManager.sendNotificationCommandSender(sender);

    }

    @Override
    public List<String> runTabComplete() {
        return null;
    }

}
