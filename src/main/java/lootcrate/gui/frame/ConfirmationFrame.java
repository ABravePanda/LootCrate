package lootcrate.gui.frame;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.AbstractGuiFrame;
import lootcrate.managers.GuiManager;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ConfirmationFrame extends AbstractGuiFrame {

    private final Runnable onConfirm;
    private final Runnable onCancel;

    private final GuiFrame previousFrame;

    private final String centerTitle;
    private final String[] centerLore;

    public ConfirmationFrame(LootCrate plugin, Player viewer, GuiFrame previousFrame, String title, String centerTitle, String[] centerLore, Runnable onConfirm, Runnable onCancel) {
        super(plugin, 27, "§8» " + title, viewer);
        this.previousFrame = previousFrame;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
        this.centerTitle = centerTitle;
        this.centerLore = centerLore;
    }

    @Override
    public void render() {
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, getJavaPlugin()).name(" ").build();

        for (int i = 0; i < getSize(); i++) {
            setItem(GUIItem.of(filler, i));
        }

        // Confirm
        setItem(GUIItem.builder()
                .slot(11)
                .itemStack(new ItemBuilder(Material.LIME_DYE, getJavaPlugin())
                        .name("§a§lConfirm")
                        .lore("§7Click to proceed.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().sendMessage("§aAction confirmed.");
                    if (onConfirm != null) onConfirm.run();
                    ctx.getFrame().setReadyToClose();
                    ctx.getPlayer().closeInventory();
                })
                .build());

        // Cancel
        setItem(GUIItem.builder()
                .slot(15)
                .itemStack(new ItemBuilder(Material.RED_DYE, getJavaPlugin())
                        .name("§c§lCancel")
                        .lore("§7Click to cancel.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().sendMessage("§cAction cancelled.");
                    if (onCancel != null) onCancel.run();
                    ctx.getFrame().setReadyToClose();
                    ctx.getPlugin().getManager(GuiManager.class).open(ctx.getPlayer(), previousFrame);
                })
                .build());

        // Center Description
        setItem(GUIItem.builder()
                .slot(13)
                .itemStack(new ItemBuilder(Material.PAPER, getJavaPlugin())
                        .name(centerTitle)
                        .lore(centerLore)
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .build());
    }
}
