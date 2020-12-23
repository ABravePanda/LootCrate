package lootcrate.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.ImmutableMap;

import lootcrate.LootCrate;
import lootcrate.managers.CrateManager;
import lootcrate.managers.InventoryManager;
import lootcrate.managers.MessageManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.other.Message;
import lootcrate.other.Placeholder;
import lootcrate.utils.ObjUtils;

public class LootCrateInteractEvent implements Listener
{
    LootCrate plugin;
    MessageManager messageManager;
    CrateManager crateManager;
    InventoryManager invManager;

    public LootCrateInteractEvent(LootCrate plugin)
    {
	this.plugin = plugin;
	this.messageManager = plugin.messageManager;
	this.crateManager = plugin.crateManager;
	this.invManager = plugin.invManager;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e)
    {
	Player p = e.getPlayer();
	if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)
	{
	    // if the player clicked on a lootcrate
	    if (plugin.locationManager.getLocationList().containsKey(e.getClickedBlock().getLocation()))
	    {
		// cancel it
		e.setCancelled(true);

		// get the crate
		Crate crate = crateManager.getCrateById(
			plugin.locationManager.getLocationList().get(e.getClickedBlock().getLocation()).getId());

		if (!p.hasPermission("lootcrate.interact." + crate.getId()))
		{
		    messageManager.sendMessage(p, Message.NO_PERMISSION_LOOTCRATE_INTERACT, ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
		    return;
		}

		// if right clicked
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
		    ItemStack item = p.getInventory().getItemInMainHand();

		    // if they clicked w/same item as key && they match

		    if (crate.getKey() == null)
		    {
			messageManager.sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
				ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, crate.getId() + ""));
			return;
		    }
		    if (crate.getItems().size() == 0)
			return;

		    if (item.getType().equals(crate.getKey().getItem().getType())
			    && ObjUtils.doKeysMatch(plugin, item, crate))
		    {
			// remove item
			p.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
			p.updateInventory();

			// play sound
			p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1f);

			// notify
			messageManager.sendMessage(p, Message.LOOTCRATE_OPEN, ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));

			CrateItem crateItem = crateManager.getRandomItem(crate);
			int rnd = crateManager.getRandomAmount(crateItem);

			// TODO open crate
			if (!crateItem.isDisplay())
			{
			    for (int i = 0; i < rnd; i++)
				p.getInventory().addItem(crateItem.getItem());
			}

			int i = 1;

			for (String cmd : crateItem.getCommands())
			{
			    if (Boolean.parseBoolean(
				    messageManager.parseMessage(Message.DISPATCH_COMMAND_ITEM_AMOUNT, null)))
				i = rnd;
			    for (int j = 0; j < i; j++)
				Bukkit.dispatchCommand(plugin.getServer().getConsoleSender(),
					cmd.replace("{player}", p.getName()));
			}
		    } else
		    {
			messageManager.sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
				ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
			return;
		    }
		}

		// if left clicked
		if (e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
		    if (e.getHand() == EquipmentSlot.HAND)
			invManager.openCrateInventory(p, crate);
		}
	    }
	}
    }

    @EventHandler
    public void onInventoryInteractEvent(InventoryInteractEvent e)
    {
	if (e.getWhoClicked() instanceof Player)
	{
	    Player p = (Player) e.getWhoClicked();
	    if (invManager.isInInventory(p))
		e.setCancelled(true);
	}
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e)
    {
	if (e.getWhoClicked() instanceof Player)
	{
	    Player p = (Player) e.getWhoClicked();
	    if (invManager.isInInventory(p))
		e.setCancelled(true);
	}
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e)
    {
	Player p = (Player) e.getPlayer();
	invManager.closeCrateInventory(p);
    }
}
