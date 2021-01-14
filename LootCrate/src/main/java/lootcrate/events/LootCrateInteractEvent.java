package lootcrate.events;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import lootcrate.managers.OptionManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.other.Message;
import lootcrate.other.Option;
import lootcrate.other.Permission;
import lootcrate.other.Placeholder;
import lootcrate.utils.InventoryUtils;
import lootcrate.utils.ObjUtils;
import lootcrate.utils.PlayerUtils;

public class LootCrateInteractEvent implements Listener
{
    LootCrate plugin;
    MessageManager messageManager;
    CrateManager crateManager;
    InventoryManager invManager;
    OptionManager optionManager;

    public LootCrateInteractEvent(LootCrate plugin)
    {
	this.plugin = plugin;
	this.messageManager = plugin.messageManager;
	this.crateManager = plugin.crateManager;
	this.invManager = plugin.invManager;
	this.optionManager = plugin.optionManager;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e)
    {
	Player p = e.getPlayer();

	if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)
	{
	    if (e.getHand() != EquipmentSlot.HAND)
		return;
	    // if the player clicked on a lootcrate
	    if (plugin.locationManager.getLocationList().containsKey(e.getClickedBlock().getLocation()))
	    {
		// cancel it
		e.setCancelled(true);

		// get the crate
		Crate crate = crateManager.getCrateById(
			plugin.locationManager.getLocationList().get(e.getClickedBlock().getLocation()).getId());

		if (!p.hasPermission(Permission.LOOTCRATE_INTERACT.getKey() + crate.getId())
			&& !p.hasPermission(Permission.LOOTCRATE_INTERACT_ADMIN.getKey()))
		{
		    messageManager.sendMessage(p, Message.NO_PERMISSION_LOOTCRATE_INTERACT,
			    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
		    return;
		}

		// if right clicked
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
		    ItemStack item = p.getInventory().getItemInMainHand();

		    // if they clicked w/same item as key && they match

		    if (crate.getKey() == null || item == null)
		    {
			messageManager.sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY, ImmutableMap
				.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, crate.getId() + ""));
			PlayerUtils.knockBackPlayer(crate, p);
			return;
		    }

		    // if no items
		    if (crate.getItems().size() == 0)
			return;

		    // if the keys match
		    if (item.getType().equals(crate.getKey().getItem().getType())
			    && ObjUtils.doKeysMatch(plugin, item, crate))
		    {
			// if inv is full
			if (InventoryUtils.isFull(p.getInventory()))
			{
			    messageManager.sendMessage(p, Message.INVENTORY_FULL, null);
			    return;
			}
			// remove item
			p.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
			p.updateInventory();

			// play sound
			crateManager.crateOpenEffects(crate, p);

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
			    if (optionManager.valueOf(Option.DISPATCH_COMMAND_ITEM_AMOUNT))
				i = rnd;
			    for (int j = 0; j < i; j++)
				Bukkit.dispatchCommand(plugin.getServer().getConsoleSender(),
					cmd.replace("{player}", p.getName()));
			}
		    } else
		    {
			messageManager.sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
				ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
			PlayerUtils.knockBackPlayer(crate, p);
			return;
		    }
		}

		// if left clicked
		if (e.getAction() == Action.LEFT_CLICK_BLOCK)
		    invManager.openCrateInventory(p, crate);
	    } else
	    {
		if (ObjUtils.isKey(plugin, p.getInventory().getItemInMainHand()))
		{
		    e.setCancelled(true);
		    messageManager.sendMessage(p, Message.CANNOT_PLACE_LOOTKEY, null);
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
	if (invManager.isInInventory(p))
	    invManager.closeCrateInventory(p);
    }
}
