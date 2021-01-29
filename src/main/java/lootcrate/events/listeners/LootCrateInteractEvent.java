package lootcrate.events.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import lootcrate.LootCrate;
import lootcrate.events.custom.CrateAccessEvent;
import lootcrate.managers.CacheManager;
import lootcrate.managers.InventoryManager;
import lootcrate.managers.MessageManager;
import lootcrate.objects.Crate;
import lootcrate.other.Message;
import lootcrate.utils.ObjUtils;

public class LootCrateInteractEvent implements Listener
{
    private LootCrate plugin;
    private MessageManager messageManager;
    private CacheManager cacheManager;
    private InventoryManager invManager;

    public LootCrateInteractEvent(LootCrate plugin)
    {
	this.plugin = plugin;
	this.messageManager = plugin.messageManager;
	this.cacheManager = plugin.cacheManager;
	this.invManager = plugin.invManager;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e)
    {
	Player p = e.getPlayer();

	if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)
	{
	    if (e.getHand() != EquipmentSlot.HAND)
		return;
	    
	    if (plugin.locationManager.getLocationList().containsKey(e.getClickedBlock().getLocation()))
	    {
		e.setCancelled(true);
		
		Crate crate = cacheManager.getCrateById(plugin.locationManager.getLocationList().get(e.getClickedBlock().getLocation()).getId());
		
		CrateAccessEvent event = new CrateAccessEvent(crate, p, e.getAction());
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled()) e.setCancelled(true);		  
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
