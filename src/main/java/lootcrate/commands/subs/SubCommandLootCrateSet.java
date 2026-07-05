package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.managers.CacheManager;
import lootcrate.managers.HologramManager;
import lootcrate.objects.Crate;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.TabUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateSet extends SubCommand {
    private final LootCrate plugin;
    private final String[] args;
    private final CommandSender sender;
    private final HologramManager hologramManager;

    public SubCommandLootCrateSet(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_SET, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        this.hologramManager = plugin.getHoloManager();
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (testPlayer(playerRequired) || !testPermissions()) return;

        Player player = (Player) sender;

        if (args.length < 2) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
            return;
        }

        Location location = player.getTargetBlock(null, 10).getLocation();

        if (args[1].equalsIgnoreCase("none")) {
            removeCrateLocation(location);
            return;
        }

        Integer crateId = CommandUtils.tryParse(args[1]);
        if (crateId == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_USAGE, null);
            return;
        }

        Crate crate = cacheManager.getCrateById(crateId);
        if (crate == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND, ImmutableMap.of(Placeholder.CRATE_ID, String.valueOf(crateId)));
            return;
        }

        ImmutableMap<Placeholder, String> placeholders = getPlaceholders(location, crate);

        if (locationManager.getLocationList().containsKey(location)) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_FAILURE, placeholders);
            return;
        }

        locationManager.addCrateLocation(location, crate);
        hologramManager.createHologram(location.getBlock(), crate);

        messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_SUCCESS, placeholders);
    }

    private void removeCrateLocation(Location location) {
        locationManager.removeCrateLocation(location);
        hologramManager.reload();

        messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_SET_REMOVE_SUCCESS, getLocationPlaceholders(location));
    }

    private ImmutableMap<Placeholder, String> getLocationPlaceholders(Location location) {
        return ImmutableMap.of(
                Placeholder.X, String.valueOf(location.getBlockX()),
                Placeholder.Y, String.valueOf(location.getBlockY()),
                Placeholder.Z, String.valueOf(location.getBlockZ())
        );
    }

    private ImmutableMap<Placeholder, String> getPlaceholders(Location location, Crate crate) {
        return ImmutableMap.of(
                Placeholder.CRATE_ID, String.valueOf(crate.getId()),
                Placeholder.CRATE_NAME, crate.getName(),
                Placeholder.X, String.valueOf(location.getBlockX()),
                Placeholder.Y, String.valueOf(location.getBlockY()),
                Placeholder.Z, String.valueOf(location.getBlockZ())
        );
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<>();

        if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_SET.getKey()) && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey())) return list;

        CacheManager cacheManager = plugin.getManager(CacheManager.class);

        if (args.length == 2) {
            list.add("[CrateID]");
            list.add("none");
            TabUtils.addCratesNamesToList(list, cacheManager);
        }

        return list;
    }
}