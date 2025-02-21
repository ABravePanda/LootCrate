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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class SubCommandLootCrateAdd extends SubCommand {
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
    public SubCommandLootCrateAdd(LootCrate plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args, Permission.COMMAND_LOOTCRATE_ADD, Permission.COMMAND_LOOTCRATE_ADMIN);
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    /**
     * Is called when the {@link lootcrate.commands.SubCommand} class calls upon it.
     * This SubCommand is used for the creation of a LootCrate. It guarantees all arguments
     * must be valid. It generates a {@link lootcrate.objects.Crate}
     *
     * @param playerRequired if a player is required to run this command can any {@link org.bukkit.command.CommandSender}
     *
     */
    @Override
    public void runSubCommand(boolean playerRequired) {
        if (this.testPlayer(playerRequired))
            return;

        Player p = (Player) sender;
        ItemStack mainHandItem = p.getInventory().getItemInMainHand();


        if (!this.testPermissions())
            return;

        if (args.length <= 5) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
            return;
        }
        if (mainHandItem.getType() == Material.AIR) {
            messageManager.sendMessage(sender, Message.MUST_HOLD_ITEM, null);
            return;
        }

        if (CommandUtils.tryParse(args[2]) == null || CommandUtils.tryParse(args[3]) == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_USAGE, null);
            return;
        }

        Crate crate = cacheManager.getCrateById(CommandUtils.tryParse(args[1]));
        if (crate == null) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_NOT_FOUND,
                    ImmutableMap.of(Placeholder.CRATE_ID, "" + CommandUtils.tryParse(args[1])));
            return;
        }
        int min = CommandUtils.tryParse(args[2]);
        int max = CommandUtils.tryParse(args[3]);
        double chance = CommandUtils.tryParseDouble(args[4]);

        if (min > max && min >= 1) {
            messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_MINMAX, null);
            return;
        }

        if(mainHandItem.getType() == Material.AIR) return;

        CrateItem item = new CrateItem(mainHandItem, min, max, chance,
                Boolean.parseBoolean(args[5]), null);
        crate.addItem(item);
        cacheManager.update(crate);
        cacheManager.reload();
        messageManager.sendMessage(sender, Message.LOOTCRATE_COMMAND_ADD_SUCCESS,
                ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
                        Placeholder.ITEM_TYPE, "" + item.getItem().getType(), Placeholder.ITEM_ID, "" + item.getId(),
                        Placeholder.ITEM_NAME, "" + item.getItem().getItemMeta().getDisplayName()));

    }

    /**
     * Compiles a list of what Strings the player chose during the TabEvent for further processing
     *
     * @return a list of all the players tabbed results
     */
    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();

        if (!sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADD.getKey())
                && !sender.hasPermission(Permission.COMMAND_LOOTCRATE_ADMIN.getKey()))
            return list;

        if (args.length == 2) {
            list.add("[CrateID]");
            TabUtils.addCratesToList(list, cacheManager);
        }
        if (args.length == 3)
            list.add("[MinimumAmount]");
        if (args.length == 4)
            list.add("[MaximumAmount]");
        if (args.length == 5)
            list.add("[Chance]");
        if (args.length == 6) {
            list.add("[Is Display]");
            list.add(ChatColor.stripColor(messageManager.parseMessage(Message.LOOTCRATE_COMMAND_ADD_HINT, null)));
            list.add("true");
            list.add("false");
        }

        return list;
    }

}
