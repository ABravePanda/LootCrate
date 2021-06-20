package lootcrate.commands;

import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    private final LootCrate plugin;
    private final CommandSender sender;
    private final String[] args;
    private final Permission[] permissions;

    public SubCommand(LootCrate plugin, CommandSender sender, String[] args, Permission... perm) {
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        this.permissions = perm;
    }

    /**
     * Checks if sender has the given permissions
     *
     * @param sender Sender to check
     * @param perm   Permissions to check
     * @return True if even just one permission is passed. False if none pass
     */
    public boolean hasPermission(Permission... perm) {
        for (Permission p : perm)
            if (sender.hasPermission(p.getKey()))
                return true;
        return false;
    }

    /**
     * Tests if sender has the given permissions for command
     *
     * @param sender Sender to check if they have permissions
     * @return
     */
    public boolean testPermissions() {
        if (!hasPermission(permissions)) {
            plugin.getMessageManager().sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
            return false;
        }

        return true;
    }

    /**
     * Tests if a player is required to execute the command
     *
     * @param playerRequired true if player is required, false if not
     * @return
     */
    public boolean testPlayer(boolean playerRequired) {
        if (playerRequired && !(sender instanceof Player)) {
            plugin.getMessageManager().sendMessage(sender, Message.MUST_BE_PLAYER, null);
            return true;
        }
        return false;
    }

    public abstract void runSubCommand(boolean playerRequired);

    public abstract List<String> runTabComplete();

}