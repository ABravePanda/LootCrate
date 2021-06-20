package lootcrate.gui.events.custom;

import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIItemClickEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled;
    private final Frame frame;
    private final InventoryClickEvent clickEvent;

    public GUIItemClickEvent(InventoryClickEvent clickEvent, Frame frame) {
        this.clickEvent = clickEvent;
        this.frame = frame;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        if (clickEvent.getWhoClicked() instanceof Player)
            return (Player) clickEvent.getWhoClicked();
        return null;
    }

    public GUIItem getItem() {
        return frame.getContents()[clickEvent.getSlot()];
    }

    public Frame getFrame() {
        return this.frame;
    }

    public InventoryClickEvent getClickEvent() {
        return clickEvent;
    }

    public boolean sameFrame(Frame frame) {
        return this.frame == frame;
    }

}
