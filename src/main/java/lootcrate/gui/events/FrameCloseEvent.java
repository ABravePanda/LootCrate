package lootcrate.gui.events;

import lootcrate.gui.Frame;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;


public class FrameCloseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Frame frame;
    private final UUID playerUUID;
    private final boolean manual;
    private boolean cancelled;

    public FrameCloseEvent(Frame frame, UUID playerUUID, boolean manual) {
        this.frame = frame;
        this.playerUUID = playerUUID;
        this.manual = manual;
        this.cancelled = false;
    }

    public Frame getFrame() {
        return frame;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public boolean isManual() {
        return manual;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}