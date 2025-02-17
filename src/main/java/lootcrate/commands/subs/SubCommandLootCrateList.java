package lootcrate.commands.subs;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.commands.SubCommand;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.objects.Crate;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SubCommandLootCrateList extends SubCommand {
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
    public SubCommandLootCrateList(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_LIST, Permission.COMMAND_LOOTCRATE_ADMIN);
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

        if (args.length != 1) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_LIST_USAGE, null);
            return;
        }

        List<Crate> crates = cacheManager.getCache();
        for (Crate crate : crates) {
            Map<Placeholder, String> map = new HashMap<Placeholder, String>();
            map.put(Placeholder.CRATE_ID, crate.getId() + "");
            map.put(Placeholder.CRATE_NAME, crate.getName());
            map.put(Placeholder.CRATE_ITEM_COUNT, crate.getItems().size() + "");
            map.put(Placeholder.CRATE_KEY_TYPE,
                    crate.getKey().getItem() == null ? "None" : crate.getKey().getItem().getType() + "");
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_LIST_FORMAT, ImmutableMap.copyOf(map));
        }
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        return list;
    }

}
