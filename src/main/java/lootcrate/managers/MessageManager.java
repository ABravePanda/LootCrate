package lootcrate.managers;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.regex.Matcher;

public class MessageManager extends BasicManager {
    private final String PREFIX = "messages.";

    /**
     * Constructor of MessageManager
     *
     * @param plugin Instance of plugin
     */
    public MessageManager(LootCrate plugin) {
        super(plugin);
    }

    /**
     * Sends a message to specified player
     *
     * @param p            Player to whom shall recieve the message
     * @param message      Message to be sent
     * @param placeholders Placeholders to be replaced in message
     */
    public void sendMessage(CommandSender p, Message message, ImmutableMap<Placeholder, String> placeholders) {
        String msg = this.parseMessage(message, placeholders);
        if (msg != null && p != null)
            p.sendMessage(this.parseMessage(Message.PREFIX, null) + msg);
    }

    /**
     * Sends a message to specified player without the prefix
     *
     * @param p            Player to whom shall recieve the message
     * @param message      Message to be sent
     * @param placeholders Placeholders to be replaced in message
     */
    public void sendNoPrefixMessage(CommandSender p, Message message, ImmutableMap<Placeholder, String> placeholders) {
        String msg = this.parseMessage(message, placeholders);
        if (msg != null)
            p.sendMessage(msg);
    }

    /**
     * Converts all placeholders in message
     *
     * @param message      Message to be fixed
     * @param placeholders Placeholders to be replaced in message
     * @return Message with placeholders replaced
     */
    public String parseMessage(Message message, ImmutableMap<Placeholder, String> placeholders) {
        String msg = this.getPlugin().getConfig().getString(PREFIX + message.getKey());

        if (msg == null || msg.isEmpty())
            return ChatColor.RED + "Cannot find string {" + message.getKey() + "}";

        String colorMsg = ChatColor.translateAlternateColorCodes('&', msg);

        if (placeholders != null) {
            for (Map.Entry<Placeholder, String> entry : placeholders.entrySet()) {
                colorMsg = colorMsg.replaceAll("\\{" + entry.getKey().getKey() + "\\}",
                        Matcher.quoteReplacement(entry.getValue()));
            }
        }
        return colorMsg;
    }

    private void validateMessages()
    {
        for(Message m : Message.values())
        {
            validateMessage(m);
        }
        this.getPlugin().saveConfig();
    }

    private void validateMessage(Message message)
    {
        if(!this.getPlugin().getConfig().contains(PREFIX + message.getKey()))
        {
            if(this.getPlugin().getConfig().get(PREFIX + message.getKey()) != null) return;
            this.getPlugin().getConfig().addDefault(PREFIX + message.getKey(), message.getDefaultValue());
            this.getPlugin().getConfig().set(PREFIX + message.getKey(), message.getDefaultValue());
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Added Config Option: " + ChatColor.YELLOW
                    + message.getKey() + ChatColor.DARK_GRAY + ".");
        }
    }

    public String getPrefix() {
        return this.parseMessage(Message.PREFIX, null);
    }

    /**
     * Sends a notification that crate is not complete
     *
     * @param crate  Referenced crate
     * @param sender Whom shall receive the message
     */
    @Deprecated
    public void crateNotification(Crate crate, CommandSender sender) {
        if (crate.getChanceCount() != 100) {
            this.sendMessage(sender, Message.LOOTCRATE_CHANCE_NOT_100,
                    ImmutableMap.of(Placeholder.CRATE_ID, "" + crate.getId(), Placeholder.CRATE_NAME, crate.getName(),
                            Placeholder.TOTAL_CRATE_CHANCE, "" + crate.getChanceCount()));
        }
    }

    @Override
    public void enable() {
        validateMessages();
    }

    @Override
    public void disable() {

    }
}
