package lootcrate.gui.frames.menu.option;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import net.md_5.bungee.api.ChatColor;

public class CrateOptionMainMenuFrame extends BasicFrame implements Listener
{

    private LootCrate plugin;
    private Crate crate;

    public CrateOptionMainMenuFrame(LootCrate plugin, Player p, Crate crate)
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
	this.setItem(10,
		new GUIItem(10, Material.NAME_TAG, ChatColor.RED + "Name", ChatColor.GRAY + "Change the crate name."));
	this.setItem(13, new GUIItem(13, Material.STICK, ChatColor.RED + "Knockback Level",
		ChatColor.GRAY + "Change how far the crate knocks you back."));
	this.setItem(16, new GUIItem(16, Material.JUKEBOX, ChatColor.RED + "Open Sound",
		ChatColor.GRAY + "Change the sound that plays when a crate is opened."));
	this.setItem(30, new GUIItem(30, Material.BLAZE_POWDER, ChatColor.RED + "Animation Style",
		ChatColor.GRAY + "Change the open animation."));
	this.setItem(32, new GUIItem(32, Material.COMMAND_BLOCK, ChatColor.RED + "Open Message",
		ChatColor.GRAY + "Change the message player recieves upon crate opening."));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e)
    {
	if (!e.sameFrame(this))
	    return;

	Player p = e.getPlayer();
	ItemStack item = e.getItem().getItemStack();

	Frame frameToOpen = null;

	switch (item.getType())
	{
	case STICK:
	    frameToOpen = new CrateOptionKnockBackFrame(plugin, p, crate);
	    break;
	case BLAZE_POWDER:
	    frameToOpen = new CrateOptionAnimationFrame(plugin, p, crate);
	    break;
	case NAME_TAG:
	    ChatState state = ChatState.CHANGE_CRATE_NAME;
	    state.setCrate(crate);
	    plugin.getChatManager().addPlayer(p, state);
	    plugin.getChatManager().sendNotification(p);
	    this.close();
	    return;
	case COMMAND_BLOCK:
	    ChatState state1 = ChatState.CHANGE_CRATE_MESSAGE;
	    state1.setCrate(crate);
	    plugin.getChatManager().addPlayer(p, state1);
	    plugin.getChatManager().sendNotification(p);
	    this.close();
	    return;
	case JUKEBOX:
	    ChatState state2 = ChatState.CHANGE_CRATE_SOUND;
	    state2.setCrate(crate);
	    plugin.getChatManager().addPlayer(p, state2);
	    plugin.getChatManager().sendNotification(p);
	    this.close();
	    return;
	default:
	    return;
	}

	this.close();
	frameToOpen.open();
    }

}