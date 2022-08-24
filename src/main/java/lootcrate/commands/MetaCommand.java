package lootcrate.commands;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import lootcrate.enums.Placeholder;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class MetaCommand extends Command {
    private final LootCrate plugin;
    private final String[] args;
    private final CommandSender sender;

    public MetaCommand(LootCrate plugin, String[] args, CommandSender sender) {
        this.plugin = plugin;
        this.args = args;
        this.sender = sender;
    }

    @Override
    public void executeCommand() {
        // sender must be player
        if (!(sender instanceof Player)) {
            plugin.getMessageManager().sendMessage(sender, Message.MUST_BE_PLAYER, null);
            return;
        }

        Player p = (Player) sender;

        // player needs to have permission
        if (!p.hasPermission(Permission.COMMAND_META.getKey())) {
            plugin.getMessageManager().sendMessage(sender, Message.NO_PERMISSION_COMMAND, null);
            return;
        }

        if (p.getInventory().getItemInMainHand() == null
                || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            plugin.getMessageManager().sendMessage(sender, Message.MUST_HOLD_ITEM, null);
            return;
        }

        ItemStack item = p.getInventory().getItemInMainHand();

        // need right amount of args
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("name"))
                p.getInventory().setItemInMainHand(ItemUtils.setDisplayName(item, CommandUtils.builder(args, 1)));
            else if (args[0].equalsIgnoreCase("lore"))
                p.getInventory().setItemInMainHand(ItemUtils.setLore(item, CommandUtils.builder(args, 1).split("\\|")));
            else if (args[0].equalsIgnoreCase("enchantment")) {
                if (args.length != 3) {
                    plugin.getMessageManager().sendMessage(sender, Message.META_USAGE, null);
                    return;
                }
                if (Enchantment.getByName(args[1]) == null) {
                    plugin.getMessageManager().sendMessage(sender, Message.ENCHANTMENT_NOT_FOUND,
                            ImmutableMap.of(Placeholder.ENCHANTMENT_NAME, args[1]));
                    return;
                }
                if (CommandUtils.tryParse(args[2]) == null) {
                    plugin.getMessageManager().sendMessage(sender, Message.META_USAGE, null);
                    return;
                }

                item.addUnsafeEnchantment(Enchantment.getByName(args[1]), Integer.parseInt(args[2]));

            } else if (args[0].equalsIgnoreCase("view"))
            {
                p.sendMessage(item.getItemMeta().getAsString());
                return;
            } else
                plugin.getMessageManager().sendMessage(sender, Message.META_USAGE, null);
            return;
        } else
            plugin.getMessageManager().sendMessage(sender, Message.META_USAGE, null);
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();
        if (args.length == 1) {
            list.add("name");
            list.add("lore");
            list.add("enchantment");
            list.add("view");
            return list;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("name"))
                list.add("[Name]");
            if (args[0].equalsIgnoreCase("lore"))
                list.add("[Line1|Line2|Line3|...]");
            if (args[0].equalsIgnoreCase("enchantment")) {
                list.add("[Enchantment]");
                for (Enchantment enchantment : Enchantment.values()) {
                    list.add(enchantment.getName());
                }
            }

        }
        return list;
    }

}
