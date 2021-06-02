package com.tompkins_development.spigot.lootcrate.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.tompkins_development.spigot.lootcrate.objects.Crate;

public class CrateViewEvent extends Event implements Cancellable
{
    private Crate crate;
    private Player player;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public CrateViewEvent(Crate crate, Player p)
    {
	this.crate = crate;
	this.player = p;
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

}
