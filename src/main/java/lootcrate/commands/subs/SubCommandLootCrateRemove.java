package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateRemove extends SubCommand {
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
    public SubCommandLootCrateRemove(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_REMOVE, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;
        if (!this.testPermissions())
            return;

        if (args.length <= 2) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_REMOVE_USAGE, null);
            return;
        }
        if (CommandUtils.tryParse(args[1]) == null || CommandUtils.tryParse(args[2]) == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_REMOVE_USAGE, null);
            return;
        }
        Crate crate = cacheManager.getCrateById(CommandUtils.tryParse(args[1]));
        if (crate == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
                    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
            return;
        }
        CrateItem item = crate.getItem(CommandUtils.tryParse(args[2]));
        if (item == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_ITEM_NOT_FOUND,
                    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1]), Placeholder.ITEM_ID,
                            "" + CommandUtils.tryParse(args[2])));
            return;
        }
        crate.removeItem(item);
        cacheManager.update(crate);
        messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_REMOVE_SUCCESS,
                ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
                        Placeholder.ITEM_ID, "" + CommandUtils.tryParse(args[2])));
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_REMOVE.getKey())
                && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
            return list;

        if (args.length == 2) {
            list.add("[CrateID]");
            TabUtils.addCratesToList(list, cacheManager);
        }
        if (args.length == 3) {
            list.add("[ItemID]");
            TabUtils.addCrateItemsToListFromID(list, cacheManager, Integer.parseInt(args[1]));
        }
        return list;
    }

}
