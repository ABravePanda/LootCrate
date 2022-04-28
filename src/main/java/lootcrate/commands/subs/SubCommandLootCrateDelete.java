package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.objects.Crate;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateDelete extends SubCommand {
    private final String[] args;
    private final CommandSender sender;
    private final LootCrate plugin;

    public SubCommandLootCrateDelete(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_DELETE, Permission.COMMAND_LOOTCRATE_ADMIN);
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

        if (args.length <= 1) {
            plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_DELETE_USAGE, null);
            return;
        }
        if (CommandUtils.tryParse(args[1]) == null) {
            plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
            return;
        }

        Crate crate = plugin.getCacheManager().getCrateById(CommandUtils.tryParse(args[1]));
        if (crate == null) {
            plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
                    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
            return;
        }
        plugin.getLocationManager().removeCrateLocation(crate);
        plugin.getHoloManager().reload();
        plugin.getCacheManager().remove(crate);
        plugin.getCacheManager().save();
        plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_DELETE_SUCCESS,
                ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, "" + crate.getId()));
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_DELETE.getKey())
                && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
            return list;

        if (args.length == 2) {
            list.add("[CrateID]");
            TabUtils.addCratesToList(list, plugin.getCacheManager());
        }
        return list;
    }

}
