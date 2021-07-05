package lootcrate.events.custom;

import lootcrate.objects.Crate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class CrateAccessEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Crate crate;
    private final Player player;
    private final Location location;
    private final Action action;
    private boolean isCancelled;

    public CrateAccessEvent(Crate crate, Player p, Location location, Action action) {
        this.crate = crate;
        this.player = p;
        this.location = location;
        this.action = action;
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

    public Location getLocation() { return this.location; }

    public Action getAction() {
        return action;
    }

}
