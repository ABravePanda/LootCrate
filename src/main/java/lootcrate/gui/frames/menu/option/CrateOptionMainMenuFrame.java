package lootcrate.gui.frames.menu.option;

import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.enums.CustomizationOption;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.menu.*;
import lootcrate.gui.frames.types.BaseFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.ChatManager;
import lootcrate.managers.CustomizationManager;
import lootcrate.objects.Crate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class CrateOptionMainMenuFrame extends BaseFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateOptionMainMenuFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;

        registerFrame();
        generateFrame();
        generateNavigation();
        registerItems();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillOptions();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods

    public void fillBackground(Material m) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, m));
        }
    }

    public void fillOptions() {
        this.setItem(4,
                new GUIItem(4, Material.SLIME_BALL, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_COOLDOWN_NAME), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_COOLDOWN_LORE)));
        this.setItem(10,
                new GUIItem(10, Material.NAME_TAG, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_NAME_NAME),plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_NAME_LORE)));
        this.setItem(12, new GUIItem(12, Material.NETHERITE_SWORD, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_KNOCKBACK_NAME), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_KNOCKBACK_LORE)));
        this.setItem(14, new GUIItem(14, Material.JUKEBOX, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_OPENSOUND_NAME), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_OPENSOUND_LORE)));
        this.setItem(16, new GUIItem(16, Material.BLAZE_POWDER, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_ANIMATION_NAME), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_ANIMATION_LORE)));
        this.setItem(20, new GUIItem(20, Material.COMMAND_BLOCK, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_MESSAGE_NAME), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_MESSAGE_LORE)));
        this.setItem(22, new GUIItem(22, Material.PAPER, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_SORT_NAME), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_SORT_LORE)));
        this.setItem(24, new GUIItem(24, Material.ARMOR_STAND, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_HOLOGRAM_NAME), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_OPTIONS_HOLOGRAM_LORE)));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();
        ChatState state = ChatState.NONE;

        Frame frameToOpen = null;

        switch (item.getType()) {
            case SLIME_BALL:
                state = ChatState.CHANGE_CRATE_COOLDOWN;
                state.setCrate(crate);
                break;
            case NETHERITE_SWORD:
                state = ChatState.CHANGE_CRATE_KNOCKBACK;
                state.setCrate(crate);
                break;
            case BLAZE_POWDER:
                frameToOpen = new CrateOptionAnimationFrame(plugin, p, crate);
                break;
            case NAME_TAG:
                state = ChatState.CHANGE_CRATE_NAME;
                state.setCrate(crate);
                break;
            case COMMAND_BLOCK:
                state = ChatState.CHANGE_CRATE_MESSAGE;
                state.setCrate(crate);
                break;
            case JUKEBOX:
                if(e.getClickEvent().getAction().equals(InventoryAction.PICKUP_HALF)) {
                    state = ChatState.CHANGE_CRATE_SOUND;
                    state.setCrate(crate);
                } else
                    frameToOpen = new CrateOptionSoundFrame(plugin, p, crate);
                break;
            case ARMOR_STAND:
                frameToOpen = new CrateOptionHologramEnabledFrame(plugin, p, crate);
                break;
            case PAPER:
                frameToOpen = new CrateOptionSortFrame(plugin, p, crate);
                break;
            default:
                return;
        }

        if(state != ChatState.NONE) {
            plugin.getManager(ChatManager.class).addPlayer(p, state);
            plugin.getManager(ChatManager.class).sendNotification(p);
            e.setCancelled(true);
            this.close();
            return;
        }

        e.setCancelled(true);
        this.close();
        frameToOpen.open();
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateFrame(plugin, player, crate));
    }
}
