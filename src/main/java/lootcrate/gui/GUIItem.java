package lootcrate.gui;

import lootcrate.LootCrate;
import lootcrate.utils.ItemUtils;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.BiConsumer;

public class GUIItem implements Listener {
    private ItemStack itemStack;
    private BiConsumer<ItemStack, UUID> onClick;
    private int uuid;

    public GUIItem(LootCrate plugin, ItemStack itemStack, BiConsumer<ItemStack, UUID> onClick) {
        this.itemStack = itemStack;
        this.uuid = ItemUtils.getGadgetID(itemStack);
        this.onClick = onClick;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if(event.getItem() == null) return;
        Action action = event.getAction();
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (ItemUtils.getGadgetID(event.getItem()) == uuid) {
                if(onClick == null) return;
                onClick.accept(itemStack, event.getPlayer().getUniqueId());
            }
        }
    }


    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if (ItemUtils.getGadgetID(event.getCurrentItem()) == uuid) {
            event.setResult(Event.Result.DENY);
            if(onClick == null) return;
            onClick.accept(itemStack, event.getWhoClicked().getUniqueId());
        }
    }
}
