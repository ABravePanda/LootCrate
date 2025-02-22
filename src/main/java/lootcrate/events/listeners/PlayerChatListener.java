package lootcrate.events.listeners;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.gui.frames.creation.items.CrateItemCreationCommandsFrame;
import lootcrate.gui.frames.menu.CrateFrame;
import lootcrate.gui.frames.menu.CrateMainMenuFrame;
import lootcrate.gui.frames.menu.option.CrateOptionMainMenuFrame;
import lootcrate.managers.*;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final LootCrate plugin;

    private final MessageManager messageManager;
    private final CrateManager crateManager;
    private final ChatManager chatManager;
    private final InventoryManager inventoryManager;
    private final CacheManager cacheManager;

    public PlayerChatListener(LootCrate plugin) {
        this.plugin = plugin;

        this.messageManager = plugin.getManager(MessageManager.class);
        this.crateManager = plugin.getManager(CrateManager.class);
        this.chatManager = plugin.getManager(ChatManager.class);
        this.inventoryManager = plugin.getManager(InventoryManager.class);
        this.cacheManager = plugin.getManager(CacheManager.class);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (!chatManager.hasState(p))
            return;

        ChatState state = chatManager.getState(p);
        Crate crate = state.getCrate();
        Crate finalCrate;

        switch (state) {
            case CHANGE_CRATE_NAME:
                if (cancelAction(e, p, crate)) break;
                crate.setName(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
                messageManager.sendMessage(p, Message.LOOTCRATE_NAME_CHANGED, ImmutableMap.of(Placeholder.CRATE_NAME, ChatColor.translateAlternateColorCodes('&', e.getMessage())));
                break;
            case CHANGE_CRATE_MESSAGE:
                if (cancelAction(e, p, crate)) break;
                crate.setOption(new CrateOption(CrateOptionType.OPEN_MESSAGE,
                        ChatColor.translateAlternateColorCodes('&', e.getMessage())));
                messageManager.sendMessage(p, Message.LOOTCRATE_MESSAGE_CHANGED, ImmutableMap.of(Placeholder.MESSAGE, ChatColor.translateAlternateColorCodes('&', e.getMessage())));
                break;
            case CHANGE_CRATE_SOUND: // Not more used
                /*
                Sound sound = SoundUtils.valueOf(e.getMessage());
                if (sound == null)
                    return;
                crate.setOption(new CrateOption(CrateOptionType.OPEN_SOUND, sound.toString()));
                p.playSound(p.getLocation(), sound, 1, 1);
                sendConfirmation(p, ChatColor.GOLD + "Crate open sound has been set to " + ChatColor.YELLOW + sound.name());
                 */
                break;
            case CREATE_CRATE_NAME:
                if(e.getMessage().equalsIgnoreCase("cancel")){
                    messageManager.sendMessage(p, Message.LOOTCRATE_ACTION_CANCELED, null);
                    Bukkit.getScheduler().runTask(plugin, () -> inventoryManager.openFrame(p, new CrateMainMenuFrame(plugin, p)));
                    break;
                }
                crate = new Crate(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
                crateManager.addDefaultOptions(crate);
                cacheManager.update(crate);
                finalCrate = crate;
                Bukkit.getScheduler().runTask(plugin, () -> inventoryManager.openFrame(p, new CrateFrame(plugin, p, finalCrate)));
                break;
            case ADD_ITEM_COMMAND:
                if(e.getMessage().equalsIgnoreCase("cancel")){
                    messageManager.sendMessage(p, Message.LOOTCRATE_ACTION_CANCELED, null);
                    Crate finalCrate1 = crate;
                    Bukkit.getScheduler().runTask(plugin, () -> inventoryManager.openFrame(p, new CrateItemCreationCommandsFrame(plugin, p, finalCrate1, state.getCrateItem())));
                    break;
                }
                state.getCrateItem().getCommands().add(e.getMessage());
                crate.replaceItem(state.getCrateItem());
                cacheManager.update(crate);
                finalCrate = crate;
                Bukkit.getScheduler().runTask(plugin, () -> inventoryManager.openFrame(p, new CrateItemCreationCommandsFrame(plugin, e.getPlayer(), finalCrate, state.getCrateItem())));
                break;
            case CHANGE_CRATE_KNOCKBACK:
                if (cancelAction(e, p, crate)) break;
                double amt;
                try {
                    amt = Double.parseDouble(e.getMessage());
                } catch (NumberFormatException exception) {
                    messageManager.sendMessage(p, Message.LOOTCRATE_KNOCKBACK_CHANGED, ImmutableMap.of(Placeholder.KNOCKBACK, e.getMessage()));
                    break;
                }
                if(amt < 0 || amt > 20d) {
                    messageManager.sendMessage(p, Message.LOOTCRATE_KNOCKBACK_NOT_CHANGED, ImmutableMap.of(Placeholder.KNOCKBACK, String.valueOf(amt)));
                }
                crate.setOption(new CrateOption(CrateOptionType.KNOCK_BACK, amt));
                messageManager.sendMessage(p, Message.LOOTCRATE_KNOCKBACK_CHANGED, ImmutableMap.of(Placeholder.KNOCKBACK, String.valueOf(amt)));
                break;
            case CHANGE_CRATE_COOLDOWN:
                if(e.getMessage().equalsIgnoreCase("cancel")){
                    messageManager.sendMessage(p, Message.LOOTCRATE_ACTION_CANCELED, null);
                    Crate finalCrate1 = crate;
                    Bukkit.getScheduler().runTask(plugin, () -> inventoryManager.openFrame(p, new CrateFrame(plugin, p, finalCrate1)));
                    break;
                }
                int amount;
                try {
                    amount = Integer.parseInt(e.getMessage());
                } catch (NumberFormatException exception) {
                    messageManager.sendMessage(p, Message.LOOTCRATE_COOLDOWN_NOT_CHANGED, ImmutableMap.of(Placeholder.COOLDOWN, e.getMessage()));
                    break;
                }
                crate.setOption(new CrateOption(CrateOptionType.COOLDOWN, amount));
                messageManager.sendMessage(p, Message.LOOTCRATE_COOLDOWN_CHANGED, ImmutableMap.of(Placeholder.COOLDOWN, String.valueOf(amount)));
                break;
            default:
                return;
        }

        e.setCancelled(true);
        if(crate != null) cacheManager.update(crate);
        chatManager.removePlayer(p);

    }

    private boolean cancelAction(AsyncPlayerChatEvent e, Player p, Crate crate) {
        if(e.getMessage().equalsIgnoreCase("cancel")){
            messageManager.sendMessage(p, Message.LOOTCRATE_ACTION_CANCELED, null);
            Bukkit.getScheduler().runTask(plugin, () -> inventoryManager.openFrame(p, new CrateOptionMainMenuFrame(plugin, p, crate)));
            return true;
        }
        return false;
    }

}
