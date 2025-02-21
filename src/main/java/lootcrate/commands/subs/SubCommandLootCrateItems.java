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
import lootcrate.utils.ItemUtils;
import lootcrate.utils.TabUtils;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SubCommandLootCrateItems extends SubCommand {
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
    public SubCommandLootCrateItems(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_ITEMS, Permission.COMMAND_LOOTCRATE_ADMIN);
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

        if (args.length != 2) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_USAGE, null);
            return;
        }
        if (CommandUtils.tryParse(args[1]) == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_USAGE, null);
            return;
        }
        Crate crate = cacheManager.getCrateById(CommandUtils.tryParse(args[1]));
        if (crate == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
                    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
            return;
        }
        List<CrateItem> items = crate.getItems();
        for (CrateItem item : items) {
            Map<Placeholder, String> map = new HashMap<Placeholder, String>();
            map.put(Placeholder.ITEM_ID, item.getId() + "");
            map.put(Placeholder.ITEM_TYPE, item.getItem().getType() + "");
            map.put(Placeholder.ITEM_MIN_AMOUNT, item.getMinAmount() + "");
            map.put(Placeholder.ITEM_MAX_AMOUNT, item.getMaxAmount() + "");
            map.put(Placeholder.ITEM_CHANCE, item.getChance() + "");
            map.put(Placeholder.ITEM_NAME, ItemUtils.getOrCreateItemMeta(item.getItem()).getDisplayName().length() == 0 ? "None"
                    : ItemUtils.getOrCreateItemMeta(item.getItem()).getDisplayName());
            map.put(Placeholder.ITEM_COMMANDS, item.getCommands().size() + "");
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ITEMS_FORMAT, ImmutableMap.copyOf(map));
        }
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_ITEMS.getKey())
                && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
            return list;

        if (args.length == 2) {
            list.add("[CrateID]");
            TabUtils.addCratesToList(list, cacheManager);
        }
        return list;
    }

}
