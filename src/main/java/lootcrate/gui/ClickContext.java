package lootcrate.gui;

import lombok.Getter;
import lootcrate.LootCrate;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.managers.GuiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ClickContext {

    private final Player player;
    private final GuiFrame frame;
    private final GUIItem item;
    private final InventoryClickEvent rawEvent;
    private final LootCrate plugin;
    private final GuiManager guiManager;

    public ClickContext(LootCrate plugin, Player player, GuiFrame frame, GUIItem item, InventoryClickEvent rawEvent) {
        this.player = player;
        this.frame = frame;
        this.item = item;
        this.rawEvent = rawEvent;
        this.plugin = plugin;
        this.guiManager = plugin.getManager(GuiManager.class);
    }

    public boolean isClickOutside() {
        return rawEvent.getSlot() == -999;
    }

    public boolean isShiftClick() {
        return rawEvent.isShiftClick();
    }

    public boolean isNumberKeySwap() {
        return rawEvent.getClick() == ClickType.NUMBER_KEY;
    }

    public boolean isTopInventoryClick() {
        Inventory clicked = rawEvent.getClickedInventory();
        return clicked != null && clicked.equals(rawEvent.getView().getTopInventory());
    }

    public boolean isBottomInventoryClick() {
        Inventory clicked = rawEvent.getClickedInventory();
        return clicked != null && clicked.equals(rawEvent.getView().getBottomInventory());
    }

    public int getHotbarKey() {
        if (isNumberKeySwap()) {
            return rawEvent.getHotbarButton(); // 0-8 hotbar keys
        }
        return -1;
    }

    public ClickType getClickType() {
        return rawEvent.getClick();
    }

    public boolean isMiddleClick() {
        return getClickType() == ClickType.MIDDLE;
    }

    public boolean isRightClick() {
        ClickType click = getClickType();
        return click == ClickType.RIGHT || click == ClickType.SHIFT_RIGHT;
    }

    public boolean isLeftClick() {
        ClickType click = getClickType();
        return click == ClickType.LEFT || click == ClickType.SHIFT_LEFT;
    }

    public int getSlot() {
        return rawEvent.getSlot();
    }

    public ItemStack getCursorItem() {
        return rawEvent.getCursor();
    }

    public ItemStack getCurrentItem() {
        return rawEvent.getCurrentItem();
    }

}
