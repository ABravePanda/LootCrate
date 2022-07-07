package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.gui.frames.KeyViewFrame;
import lootcrate.managers.KeyCacheManager;
import lootcrate.objects.Crate;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.ObjUtils;
import lootcrate.utils.TabUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class SubCommandLootCrateClaim extends SubCommand {
    private final String[] args;
    private final CommandSender sender;
    private final LootCrate plugin;
    private final KeyCacheManager keyCacheManager;

    /**
     * Default constructor for any {@link SubCommand}
     *
     * @param plugin an instance of {@link LootCrate}
     * @param sender the {@link CommandSender} which is executing this command
     * @param args the following arguments in the command string
     *
     */
    public SubCommandLootCrateClaim(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_CLAIM, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        this.keyCacheManager = plugin.getKeyCacheManager();
    }

    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;
        if (!this.testPermissions())
            return;

        Player p = (Player) sender;

        plugin.getInvManager().openFrame(p, new KeyViewFrame(plugin, p));

    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();
        return list;
    }


}
