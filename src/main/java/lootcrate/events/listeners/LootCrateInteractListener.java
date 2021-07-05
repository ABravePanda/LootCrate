package lootcrate.events.listeners;

import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.events.custom.CrateAccessEvent;
import lootcrate.managers.CacheManager;
import lootcrate.managers.MessageManager;
import lootcrate.objects.Crate;
import lootcrate.utils.ObjUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class LootCrateInteractListener implements Listener {
    private final LootCrate plugin;
    private final MessageManager messageManager;
    private final CacheManager cacheManager;

    public LootCrateInteractListener(LootCrate plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
        this.cacheManager = plugin.getCacheManager();
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK)
            return;
        if (e.getHand() != EquipmentSlot.HAND)
            return;
        if (p.getInventory().getItemInMainHand() == null)
            return;

        Location clickedBlockLocation = e.getClickedBlock().getLocation();

        if (!isCrate(clickedBlockLocation)) {
            if (ObjUtils.isKey(plugin, p.getInventory().getItemInMainHand())) {
                e.setCancelled(true);
                messageManager.sendMessage(p, Message.CANNOT_PLACE_LOOTKEY, null);
                return;
            }
            return;
        }

        e.setCancelled(true);

        Crate crate = cacheManager.getCrateById(
                plugin.getLocationManager().getLocationList().get(e.getClickedBlock().getLocation()).getId());

        CrateAccessEvent event = new CrateAccessEvent(crate, p, e.getClickedBlock().getLocation(), e.getAction());
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled())
            e.setCancelled(true);

    }

    private boolean isCrate(Location l) {
        return plugin.getLocationManager().getLocationList().containsKey(l);
    }
}
