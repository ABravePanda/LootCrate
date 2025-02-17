package lootcrate.commands;

import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.managers.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    private final LootCrate plugin;
    private final CommandSender sender;
    private final String[] args;
    private final Permission[] permissions;

    protected final CrateManager crateManager;
    protected final CacheManager cacheManager;
    protected final LocationManager locationManager;
    protected final MessageManager messageManager;
    protected final OptionManager optionManager;
    protected final InventoryManager inventoryManager;

    public SubCommand(LootCrate plugin, CommandSender sender, String[] args, Permission... perm) {
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        this.permissions = perm;

        this.crateManager = plugin.getManager(CrateManager.class);
        this.cacheManager = plugin.getManager(CacheManager.class);
        this.messageManager = plugin.getManager(MessageManager.class);
        this.optionManager = plugin.getManager(OptionManager.class);
        this.locationManager = plugin.getManager(LocationManager.class);
        this.inventoryManager = plugin.getManager(InventoryManager.class);
    }

    /**
     * Checks if sender has the given permissions
     *
     * @param perm   Permissions to check
     * @return True if even just one permission is passed. False if none pass
     */
    public boolean hasPermission(Permission... perm) {
        if(perm == null) return true;
        for (Permission p : perm)
            if (sender.hasPermission(p.getKey()))
                return true;
        return false;
    }

    /**
     * Tests if sender has the given permissions for command
     *
     * @return
     */
    public boolean testPermissions() {
        if (!hasPermission(permissions)) {
            messageManager.sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
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
            messageManager.sendMessage(sender, Message.MUST_BE_PLAYER, null);
            return true;
        }
        return false;
    }

    public abstract void runSubCommand(boolean playerRequired);

    public abstract List<String> runTabComplete();

}