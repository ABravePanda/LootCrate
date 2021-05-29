package lootcrate.gui.frames.menu.option;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import lootcrate.LootCrate;
import lootcrate.enums.AnimationStyle;
import lootcrate.enums.CrateOptionType;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import net.md_5.bungee.api.ChatColor;

public class CrateOptionAnimationFrame extends BasicFrame implements Listener
{

    private LootCrate plugin;
    private Crate crate;
    private GUIItem csgoItem = new GUIItem(20, Material.CHEST, ChatColor.RED + "CSGO Style",
	    ChatColor.GRAY + "A scrolling type animation right from CSGO");
    private GUIItem glassItem = new GUIItem(22, Material.ENDER_CHEST, ChatColor.RED + "Glass Background",
	    ChatColor.GRAY + "Glass background randomizes with one random reward in the middle");
    private GUIItem removingItem = new GUIItem(24, Material.TNT, ChatColor.RED + "Removing Item",
	    ChatColor.GRAY + "Continuously removes items until one is left");

    public CrateOptionAnimationFrame(LootCrate plugin, Player p, Crate crate)
    {
	super(plugin, p, crate.getName());

	this.plugin = plugin;
	this.crate = crate;

	registerFrame();
	generateFrame();
	registerItems();
    }

    @Override
    public void generateFrame()
    {
	fillBackground(Material.WHITE_STAINED_GLASS_PANE);
	fillOptions();
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

    public void fillOptions()
    {

	CrateOption opt = crate.getOption(CrateOptionType.ANIMATION_STYLE);
	AnimationStyle type = AnimationStyle.valueOf((String) opt.getValue());

	switch (type)
	{
	case CSGO:
	    csgoItem.setName(ChatColor.GREEN + "CSGO Style");
	    break;
	case RANDOM_GLASS:
	    glassItem.setName(ChatColor.GREEN + "Glass Background");
	    break;
	case REMOVING_ITEM:
	    removingItem.setName(ChatColor.GREEN + "Removing Item");
	default:
	    break;

	}

	this.setItem(20, csgoItem);
	this.setItem(22, glassItem);
	this.setItem(24, removingItem);
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e)
    {
	if (!e.sameFrame(this))
	    return;

	ItemStack item = e.getItem().getItemStack();
	AnimationStyle style = AnimationStyle.RANDOM_GLASS;

	switch (item.getType())
	{
	case CHEST:
	    csgoItem.setName(ChatColor.GREEN + "CSGO Style");
	    glassItem.setName(ChatColor.RED + "Glass Background");
	    removingItem.setName(ChatColor.RED + "Removing Item");
	    style = AnimationStyle.CSGO;
	    break;
	case ENDER_CHEST:
	    glassItem.setName(ChatColor.GREEN + "Glass Background");
	    csgoItem.setName(ChatColor.RED + "CSGO Style");
	    removingItem.setName(ChatColor.RED + "Removing Item");
	    style = AnimationStyle.RANDOM_GLASS;
	    break;
	case TNT:
	    removingItem.setName(ChatColor.GREEN + "Removing Item");
	    glassItem.setName(ChatColor.RED + "Glass Background");
	    csgoItem.setName(ChatColor.RED + "CSGO Style");
	    style = AnimationStyle.REMOVING_ITEM;
	    break;
	default:
	    return;
	}

	crate.addOption(CrateOptionType.ANIMATION_STYLE, style.toString());
	plugin.getCacheManager().update(crate);
	this.setItem(20, csgoItem);
	this.setItem(22, glassItem);
	this.setItem(24, removingItem);
    }

}
