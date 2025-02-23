package lootcrate.managers;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChatManager extends BasicManager {
    private final HashMap<Player, ChatState> map;
    private MessageManager messageManager;

    /**
     * Constructor of ChatManager
     *
     * @param plugin Instance of plugin
     */
    public ChatManager(LootCrate plugin) {
        super(plugin);
        map = new HashMap<Player, ChatState>();
    }

    public void addPlayer(Player p, ChatState state) {
        map.put(p, state);
    }

    public void removePlayer(Player p) {
        map.remove(p);
    }

    public ChatState getState(Player p) {
        if (map.containsKey(p))
            return map.get(p);
        return null;
    }

    public boolean hasState(Player p) {
        return map.containsKey(p);
    }

    public void sendNotification(Player p) {
        if (!hasState(p))
            return;

        switch (getState(p)) {
            case CHANGE_CRATE_NAME:
                messageManager.sendMessage(p, Message.LOOTCRATE_CHANGE_CRATE_NAME, null);
                break;
            case CHANGE_CRATE_MESSAGE:
                messageManager.sendMessage(p, Message.LOOTCRATE_CHANGE_CRATE_MESSAGE, null);
                break;
            case CHANGE_CRATE_SOUND:
                messageManager.sendMessage(p, Message.LOOTCRATE_CHANGE_CRATE_SOUND, null);
                break;
            case CREATE_CRATE_NAME:
                messageManager.sendMessage(p, Message.LOOTCRATE_CREATE_CRATE_NAME, null);
                break;
            case ADD_ITEM_COMMAND:
                messageManager.sendMessage(p, Message.LOOTCRATE_ADD_ITEM_COMMAND, null);
                break;
            case CHANGE_CRATE_KNOCKBACK:
                messageManager.sendMessage(p, Message.LOOTCRATE_CHANGE_CRATE_KNOCKBACK, ImmutableMap.of(Placeholder.KNOCKBACK, getState(p).getCrate().getOption(CrateOptionType.KNOCK_BACK).getValue().toString()));
                break;
            case CHANGE_CRATE_COOLDOWN:
                messageManager.sendMessage(p, Message.LOOTCRATE_CHANGE_CRATE_COOLDOWN, ImmutableMap.of(Placeholder.COOLDOWN, getState(p).getCrate().getOption(CrateOptionType.COOLDOWN).getValue().toString()));
                break;
            default:
                break;

        }
    }

    @Override
    public void enable() {
        this.messageManager = getPlugin().getManager(MessageManager.class);
    }

    @Override
    public void disable() {

    }
}
