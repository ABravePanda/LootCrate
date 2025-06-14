package lootcrate.gui.event.listener;

import lootcrate.LootCrate;
import lootcrate.gui.ClickContext;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.managers.GuiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiClickListener implements Listener {


    private final LootCrate plugin;
    private final GuiManager guiManager;

    public GuiClickListener(LootCrate plugin) {
        this.plugin = plugin;
        this.guiManager = plugin.getManager(GuiManager.class);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        GuiFrame frame = guiManager.getCurrentFrame(player);
        Inventory guiInventory = guiManager.getOpenInventory(player);
        if (frame == null || guiInventory == null) return;

        int rawSlot = event.getRawSlot();
        ClickType click = event.getClick();
        GUIItem item = frame.getItemAtSlot(rawSlot);
        ClickContext ctx = new ClickContext(plugin, player, frame, item, event);

        if (ctx.isClickOutside()) return;

        boolean cancel = false;

        if (ctx.isBottomInventoryClick()) {
            if (ctx.isShiftClick()) {
                if (!frame.onItemPlace(-1, ctx.getCurrentItem())) {
                    cancel = true;
                }
            } else if (ctx.isNumberKeySwap()) {
                ItemStack fromHotbar = player.getInventory().getItem(ctx.getHotbarKey());
                if (!frame.onItemPlace(rawSlot, fromHotbar)) {
                    cancel = true;
                }
            }
        }

        if (ctx.isTopInventoryClick() && event.getClickedInventory() == guiInventory) {
            if (ctx.isNumberKeySwap()) {
                ItemStack swapped = player.getInventory().getItem(ctx.getHotbarKey());
                if (!frame.onItemPlace(rawSlot, swapped)) {
                    cancel = true;
                }
            } else if (ctx.getCursorItem() != null && !ctx.getCursorItem().getType().isAir()) {
                if (!frame.onItemPlace(rawSlot, ctx.getCursorItem())) {
                    cancel = true;
                }
            } else {
                if (!frame.onItemPickup(rawSlot, ctx.getCurrentItem())) {
                    cancel = true;
                }
            }

            if (item == null || item.shouldCancel(ctx)) {
                cancel = true;
            }
        }

        if (ctx.isTopInventoryClick() && event.getClickedInventory() == guiInventory && item != null) {
            item.handleClick(ctx);
        }

        event.setCancelled(cancel);
    }
}
