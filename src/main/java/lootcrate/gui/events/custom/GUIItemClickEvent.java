package lootcrate.gui.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;

public class GUIItemClickEvent extends Event implements Cancellable
{

    private boolean cancelled;
    private Player player;
    private GUIItem item;
    private Frame frame;
    private int slot;
    
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    
    public GUIItemClickEvent(Player p, GUIItem item, Frame frame, int slot)
    {
	this.player = p;
	this.item = item;
	this.frame = frame;
	this.slot = slot;
    }
    
    
    @Override
    public boolean isCancelled()
    {
	return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
	this.cancelled = cancelled;
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

    
    public Player getPlayer()
    {
	return this.player;
    }
    
    public GUIItem getItem()
    {
	return this.item;
    }
    
    public Frame getFrame()
    {
	return this.frame;
    }
    
    public int getSlot()
    {
	return slot;
    }

    public boolean sameFrame(Frame frame)
    {
	return this.frame == frame;
    }

}
