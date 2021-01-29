package lootcrate.gui.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;

public class GUIItemClickEvent extends Event implements Cancellable
{

    private boolean cancelled;
    private Player player;
    private GUIItem item;
    private BasicFrame frame;
    
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    
    public GUIItemClickEvent(Player p, GUIItem item, BasicFrame frame)
    {
	this.player = p;
	this.item = item;
	this.frame = frame;
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
    
    public BasicFrame getFrame()
    {
	return this.frame;
    }

}
