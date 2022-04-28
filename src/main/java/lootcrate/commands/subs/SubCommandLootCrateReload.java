package lootcrate.commands.subs;

import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SubCommandLootCrateReload extends SubCommand {
    private final CommandSender sender;
    private final LootCrate plugin;

    public SubCommandLootCrateReload(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_RELOAD, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;
        if (!this.testPermissions())
            return;

        plugin.reloadConfig();
        plugin.reload();
        plugin.getHoloManager().reload();

        plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_COMMAND_RELOAD_SUCCESS, null);
    }

    @Override
    public List<String> runTabComplete() {
        return null;
    }

}
