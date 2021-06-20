package lootcrate.gui.events.custom;

import lootcrate.gui.frames.types.Frame;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GUICloseEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled;
    private final Player player;
    private final Frame frame;

    public GUICloseEvent(Player p, Frame frame) {
        this.player = p;
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
        return this.player;
    }

    public Frame getFrame() {
        return this.frame;
    }

}
