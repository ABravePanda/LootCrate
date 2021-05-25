package lootcrate.gui.frames.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import net.md_5.bungee.api.ChatColor;

public class CrateItemFrame extends ExtendedFrame implements Listener
{

    private LootCrate plugin;
    private Crate crate;
    private static Material BACKGROUND = Material.WHITE_STAINED_GLASS_PANE;

    public CrateItemFrame(LootCrate plugin, Player p, Crate crate)
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
	fillBackground(BACKGROUND);
	fillItems();
    }

    @Override
    public void unregisterFrame()
    {
	GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods

    public void fillBackground(Material m)
    {
	for (int i = 0; i < getInventory().getSize(); i++)
	{
	    this.setItem(i, new GUIItem(i, m));
	}
    }

    public void fillItems()
    {
	int index = 0;
	for (CrateItem item : crate.getItems())
	{
	    this.setItem(index, createGUIItem(index, item));
	    index++;
	}
    }

    public GUIItem createGUIItem(int index, CrateItem item)
    {
	GUIItem guiItem = new GUIItem(index, item.getItem().clone());
	guiItem.addLoreLine(" ");
	guiItem.addLoreLine(ChatColor.DARK_GRAY + "---[Info]---");
	guiItem.addLoreLine(ChatColor.GRAY + "ID: " + ChatColor.GOLD + item.getId());
	guiItem.addLoreLine(ChatColor.GRAY + "Chance: " + ChatColor.GOLD + item.getChance() + "%");
	guiItem.addLoreLine(ChatColor.GRAY + "Min: " + ChatColor.GOLD + item.getMinAmount());
	guiItem.addLoreLine(ChatColor.GRAY + "Max: " + ChatColor.GOLD + item.getMaxAmount());
	guiItem.addLoreLine(ChatColor.GRAY + "Display: " + ChatColor.GOLD + item.isDisplay());
	guiItem.addLoreLine(ChatColor.GRAY + "Commands: ");
	for (String command : item.getCommands())
	    guiItem.addLoreLine(ChatColor.GRAY + "  - /" + ChatColor.GOLD + command);
	guiItem.addLoreLine(" ");
	guiItem.addLoreLine(ChatColor.RED + "Left-Click to Remove");
	guiItem.setCrateItem(item);
	return guiItem;
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e)
    {
	Player p = e.getPlayer();

	if (!e.sameFrame(this))
	    return;

	GUIItem itemClicked = e.getItem();

	
	if (itemClicked.getItemStack().getType() == BACKGROUND)
	    return;
	
	CrateItem item = crate.getItem(this.getContents()[e.getItem().getSlot()].getCrateItem().getId());
	crate.removeItem(item);
	plugin.cacheManager.update(crate);
	this.close();
	new CrateItemFrame(plugin, p, crate).open();
    }
}
