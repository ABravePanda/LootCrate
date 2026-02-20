package lootcrate.gui.frame.crate;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.AbstractAnimatedFrame;
import lootcrate.managers.CrateManager;
import lootcrate.managers.GuiManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Reusable crate opening animation frame.
 * <p>
 * Shows a rotating glass border while quickly cycling reward previews,
 * then gives the final reward once the animation ends.
 */
public class CrateOpeningAnimationFrame extends AbstractAnimatedFrame {

    private static final int PREVIEW_SLOT = 13;
    private static final int DURATION_TICKS = 60;

    private final LootCrate plugin;
    private final Crate crate;

    private final List<Material> borderPalette = Arrays.asList(
            Material.RED_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.LIME_STAINED_GLASS_PANE,
            Material.LIGHT_BLUE_STAINED_GLASS_PANE,
            Material.PURPLE_STAINED_GLASS_PANE
    );

    private CrateItem selectedReward;
    private int paletteIndex;
    private boolean rewardGiven;

    public CrateOpeningAnimationFrame(LootCrate plugin, Player viewer, Crate crate) {
        super(plugin, 27, "§8Opening §b" + crate.getName(), viewer, DURATION_TICKS, 2);
        this.plugin = plugin;
        this.crate = crate;
    }

    @Override
    public void render() {
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, plugin).name(" ").build();

        for (int slot = 0; slot < getSize(); slot++) {
            setItem(GUIItem.builder()
                    .slot(slot)
                    .itemStack(filler)
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .build());
        }

        applyBorderColor(borderPalette.get(paletteIndex));

        setItem(GUIItem.builder()
                .slot(PREVIEW_SLOT)
                .itemStack(getPreviewItem())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .build());
    }

    @Override
    public boolean preventsClose() {
        return isAnimationRunning();
    }

    @Override
    protected void onAnimationTick(int animationStep) {
        selectedReward = plugin.getManager(CrateManager.class).getRandomItem(crate);
        paletteIndex = (paletteIndex + 1) % borderPalette.size();
        plugin.getManager(GuiManager.class).refresh(getViewer());
    }

    @Override
    protected void onAnimationComplete() {
        if (rewardGiven || selectedReward == null) {
            return;
        }

        rewardGiven = true;
        plugin.getManager(CrateManager.class).giveReward(selectedReward, getViewer(), crate);
        plugin.getManager(GuiManager.class).close(getViewer());
    }

    private ItemStack getPreviewItem() {
        if (selectedReward == null) {
            return new ItemBuilder(Material.ENDER_CHEST, plugin)
                    .name("§eRolling reward...")
                    .lore("§7Please wait")
                    .build();
        }

        return selectedReward.getItem();
    }

    private void applyBorderColor(Material color) {
        ItemStack border = new ItemBuilder(color, plugin).name(" ").build();
        int[] borderSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

        for (int slot : borderSlots) {
            setItem(GUIItem.builder()
                    .slot(slot)
                    .itemStack(border)
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .build());
        }
    }
}
