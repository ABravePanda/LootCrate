package lootcrate.gui.frames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

public class CrateViewFrame extends BasicFrame
{

    private LootCrate plugin;
    private Crate crate;
    
    public CrateViewFrame(LootCrate plugin, Player p, Crate crate)
    {
	super(plugin, p, crate.getName());
	
	this.plugin = plugin;
	this.crate = crate;
	
	generateFrame();
	registerItems();
	registerFrame();
    }

    @Override
    public void generateFrame()
    {
	int index = 0;
	for(CrateItem item : plugin.invManager.addCrateEffects(crate))
	{
	    if(index < getInventory().getSize())
		this.setItem(index, new GUIItem(item.getItem()));
	    index++;
	}
	
	/**
	GUIItem item1 = new GUIItem(Material.STONE);
	item1.setClickHandler(new Callable<Integer>()
	{
	    public Integer call()
	    {
		System.out.println("Hello");
		return 1;
	    }
	});
	this.setItem(0, item1);
	*/
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e)
    {
	if (!((Player) e.getWhoClicked()).equals(this.getViewer()))
	    return;
	if (e.getInventory() != this.getInventory())
	    return;
	if (e.getCurrentItem() == null)
	    return;

	GUIItemClickEvent event = new GUIItemClickEvent((Player) e.getWhoClicked(), getContents()[e.getSlot()], this);
	Bukkit.getPluginManager().callEvent(event);
	if (event.isCancelled())
	    e.setCancelled(true);
    }
}
