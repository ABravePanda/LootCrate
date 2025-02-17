package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.HologramPlugin;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.managers.HologramManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateUnset extends SubCommand {
    private final String[] args;
    private final CommandSender sender;
    private final LootCrate plugin;
    private final HologramManager holoManager;

    /**
     * Default constructor for any {@link SubCommand}
     *
     * @param plugin an instance of {@link LootCrate}
     * @param sender the {@link CommandSender} which is executing this command
     * @param args the following arguments in the command string
     *
     */
    public SubCommandLootCrateUnset(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_SET, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        this.holoManager = plugin.getHoloManager();
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;

        Player p = (Player) sender;

        if (!this.testPermissions())
            return;

        if (args.length < 1) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
            return;
        }

        Location l = p.getTargetBlock(null, 10).getLocation();
        ImmutableMap<Placeholder, String> map1 = ImmutableMap.of(Placeholder.X, l.getBlockX() + "", Placeholder.Y,
                l.getBlockY() + "", Placeholder.Z, l.getBlockZ() + "");

        ImmutableMap<Placeholder, String> map = ImmutableMap.of(Placeholder.X, l.getBlockX() + "", Placeholder.Y,
                l.getBlockY() + "", Placeholder.Z, l.getBlockZ() + "");

        if(!locationManager.getLocationList().containsKey(l))
        {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_FAILURE, map);
            return;
        }
        locationManager.removeCrateLocation(l);

        // create hologram
        if(plugin.isHologramPluginDetected(HologramPlugin.DECENT_HOLOGRAMS))
            holoManager.reload();

        messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_SUCCESS, map);
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();
        return list;
    }

}
