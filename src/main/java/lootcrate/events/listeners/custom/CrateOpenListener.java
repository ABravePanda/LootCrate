package lootcrate.events.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.events.custom.CrateOpenEvent;
import lootcrate.gui.frames.CrateOpenFrame;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.other.Placeholder;
import lootcrate.utils.InventoryUtils;
import lootcrate.utils.ObjUtils;
import lootcrate.utils.PlayerUtils;

public class CrateOpenListener implements Listener
{
    private LootCrate plugin;

    public CrateOpenListener(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    @EventHandler
    public void onOpen(CrateOpenEvent e)
    {
	Player p = e.getPlayer();
	Crate crate = e.getCrate();
	ItemStack item = p.getInventory().getItemInMainHand();

	// if they clicked w/same item as key && they match

	if (crate.getKey() == null || crate.getKey().getItem() == null|| item == null)
	{
	    plugin.messageManager.sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
		    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, crate.getId() + ""));
	    PlayerUtils.knockBackPlayer(crate, p);
	    return;
	}
	
	// if no items
	if (crate.getItems().size() == 0)
	    return;

	// if the keys match
	if (!item.getType().equals(crate.getKey().getItem().getType()) || !ObjUtils.doKeysMatch(plugin, item, crate))
	{
	    plugin.messageManager.sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
		    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
	    PlayerUtils.knockBackPlayer(crate, p);
	    return;
	}
	
	// if inv is full
	if (InventoryUtils.isFull(p.getInventory()))
	{
	    plugin.messageManager.sendMessage(p, Message.INVENTORY_FULL, null);
	    return;
	}
	
	// remove item
	p.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
	p.updateInventory();

	// play sound
	plugin.crateManager.crateOpenEffects(crate, p);
	
	
	CrateOpenFrame frame = new CrateOpenFrame(plugin, p, crate, 5, 10, 5);
	frame.open();
	
	frame.showAnimation();
    }

}
