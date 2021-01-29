package lootcrate.gui.frames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lootcrate.gui.items.GUIItem;

public abstract class BasicFrame implements Frame
{
    private Player player;
    private String title;
    private GUIItem[] contents;
    private Inventory inventory;
    
    public BasicFrame(Player p, String title, GUIItem[] contents)
    {
	this.player = p;
	this.title = title;
	this.contents = contents;
	this.inventory = createInventory();
    }
    
    public BasicFrame(Player p, String title)
    {
	this.player = p;
	this.title = title;
	this.contents = new GUIItem[45];
	this.inventory = createInventory();
    }

    @Override
    public Player getViewer()
    {
	return this.player;
    }

    @Override
    public String getTitle()
    {
	return this.title;
    }

    @Override
    public GUIItem[] getContents()
    {
	return this.contents;
    }

    @Override
    public Inventory getInventory()
    {
	return this.inventory;
    }
    
    private Inventory createInventory()
    {
	return Bukkit.createInventory(null, 45, getTitle());
    }

}
