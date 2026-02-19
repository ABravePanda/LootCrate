package lootcrate.gui.frame;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.managers.GuiManager;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * A GUI frame that allows the player to set an item into the center slot.
 * Useful for things like setting crate keys or other special items.
 */
public class SetItemFrame extends AbstractGuiFrame {

    private final Runnable onCancel;
    private final Consumer<ItemStack> onSet;
    private final GuiFrame previousFrame;

    private final String centerTitle;
    private final String[] centerLore;

    private final ItemStack defaultItem;

    private final int itemSlot = 13;

    public SetItemFrame(LootCrate plugin, Player viewer, GuiFrame previousFrame, String title, String centerTitle, String[] centerLore, Consumer<ItemStack> onSet, Runnable onCancel, ItemStack defaultItem) {
        super(plugin, 27, "§8» " + title, viewer);
        this.previousFrame = previousFrame;
        this.onSet = onSet;
        this.onCancel = onCancel;
        this.centerTitle = centerTitle;
        this.centerLore = centerLore;
        this.defaultItem = defaultItem;
    }

    @Override
    public void render() {
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, getJavaPlugin()).name(" ").build();

        for (int i = 0; i < getSize(); i++) {
            setItem(GUIItem.of(filler, i));
        }

        // Save Button
        setItem(GUIItem.builder()
                .slot(11)
                .itemStack(new ItemBuilder(Material.LIME_CONCRETE, getJavaPlugin())
                        .name("§a§lSave Item")
                        .lore("§7Click to save the placed item.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ItemStack placed = getItemAtSlot(itemSlot).getItemStack();
                    if (placed == null || placed.getType() == Material.AIR) {
                        ctx.getPlayer().sendMessage("§cYou must place an item in the slot first.");
                        return;
                    }
                    if (onSet != null) onSet.accept(placed.clone());
                    ctx.getPlugin().getManager(GuiManager.class).open(ctx.getPlayer(), previousFrame);
                })
                .build());

        // Cancel Button
        setItem(GUIItem.builder()
                .slot(15)
                .itemStack(new ItemBuilder(Material.RED_CONCRETE, getJavaPlugin())
                        .name("§c§lCancel")
                        .lore("§7Click to cancel and go back.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    if (onCancel != null) onCancel.run();
                    ctx.getPlugin().getManager(GuiManager.class).open(ctx.getPlayer(), previousFrame);
                })
                .build());

        // Item Slot (player can place here)
        setItem(GUIItem.builder()
                .slot(itemSlot)
                .itemStack(defaultItem.clone())
                .cancelPolicy(CancelPolicy.NEVER) // allow placing items
                .build());
    }

    @Override
    public boolean onItemPlace(int slot, ItemStack item) {
        if (slot == itemSlot) {
            setItem(GUIItem.builder()
                    .slot(itemSlot)
                    .itemStack(item.clone())
                    .cancelPolicy(CancelPolicy.NEVER)
                    .build());
            return true;
        }
        return false;
    }

    @Override
    public boolean onItemPickup(int slot, ItemStack item) {
        return slot == itemSlot; // allow pickup from center slot
    }

    @Override
    public boolean preventsClose() {
        return false;
    }
}
