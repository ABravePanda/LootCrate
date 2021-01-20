package lootcrate.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

import lootcrate.objects.Crate;

public class CrateAccessEvent extends Event implements Cancellable
{
    private Crate crate;
    private Player player;
    private Action action;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public CrateAccessEvent(Crate crate, Player p, Action action)
    {
	this.crate = crate;
	this.player = p;
	this.action = action;
	this.isCancelled = false;
    }

    @Override
    public boolean isCancelled()
    {
	return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
	this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers()
    {
	return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList()
    {
	return HANDLERS_LIST;
    }

    public Crate getCrate()
    {
	return crate;
    }

    public Player getPlayer()
    {
	return this.player;
    }

    public Action getAction()
    {
	return action;
    }

}
