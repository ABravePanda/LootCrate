package lootcrate.events.custom;

import lootcrate.objects.Crate;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrateViewEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Crate crate;
    private final Player player;
    private boolean isCancelled;

    public CrateViewEvent(Crate crate, Player p) {
        this.crate = crate;
        this.player = p;
        this.isCancelled = false;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public Crate getCrate() {
        return crate;
    }

    public Player getPlayer() {
        return this.player;
    }

}
