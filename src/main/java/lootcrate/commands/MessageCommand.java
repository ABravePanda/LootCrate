package lootcrate.commands;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.managers.MessageManager;
import lootcrate.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class MessageCommand extends Command {

    private final LootCrate plugin;
    private final String[] args;
    private final CommandSender sender;

    public MessageCommand(LootCrate plugin, String[] args, CommandSender sender) {
        this.plugin = plugin;
        this.args = args;
        this.sender = sender;
    }

    @Override
    public void executeCommand() {
        if (sender instanceof Player) {
            plugin.getManager(MessageManager.class).sendMessage(sender, Message.MUST_BE_CONSOLE, null);
            return;
        }

        if (args.length < 2) {
            plugin.getManager(MessageManager.class).sendMessage(sender, Message.MESSAGE_COMMAND_USAGE, null);
            return;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            plugin.getManager(MessageManager.class).sendMessage(sender, Message.PLAYER_NOT_FOUND,
                    ImmutableMap.of(Placeholder.PLAYER_NAME, args[0]));
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);

        plugin.getManager(MessageManager.class).sendNoPrefixMessage(p, Message.MESSAGE_COMMAND_FORMAT,
                ImmutableMap.of(Placeholder.PLAYER, args[0], Placeholder.MESSAGE, CommandUtils.builder(args, 1)));
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();
        if (args.length == 1) {
            list.add("[Player]");
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
            return list;
        }
        if (args.length == 2)
            list.add("[Message]");
        return list;
    }

}
