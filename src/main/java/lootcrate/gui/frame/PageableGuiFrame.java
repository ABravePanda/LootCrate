package lootcrate.gui.frame;

import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public abstract class PageableGuiFrame<T> extends AbstractGuiFrame {

    protected int currentPage = 0;
    protected final int itemsPerPage;
    protected List<T> cachedElements = Collections.emptyList();

    private GuiFrame backFrame = null;

    public PageableGuiFrame(JavaPlugin plugin, int size, String title, Player viewer, int itemsPerPage) {
        super(plugin, size, title, viewer);
        this.itemsPerPage = itemsPerPage;
    }

    public void setBackFrame(GuiFrame frame) {
        this.backFrame = frame;
    }

    @Override
    public void render() {
        cachedElements = getElements();
        clearItems();

        renderBorder();
        renderPage(currentPage);
        renderNavigation();
        renderBackButton();
    }

    protected void renderPage(int page) {
        List<Integer> contentSlots = getContentSlots();

        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, cachedElements.size());
        List<T> pageItems = cachedElements.subList(startIndex, endIndex);

        for (int i = 0; i < pageItems.size(); i++) {
            int slot = contentSlots.get(i);
            GUIItem item = buildItem(pageItems.get(i), slot);
            if (item != null) setItem(item);
        }
    }

    protected void renderNavigation() {
        int maxPage = getMaxPage();

        if (currentPage > 0) {
            setItem(GUIItem.builder()
                    .slot(getPreviousButtonSlot())
                    .itemStack(new ItemBuilder(Material.RED_STAINED_GLASS_PANE, plugin)
                            .name("§cPrevious Page")
                            .lore("§8You are on page §7" + (currentPage + 1) + "/" + (maxPage + 1))
                            .build())
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .onClick(ClickType.LEFT, ctx -> {
                        currentPage--;
                        ctx.getGuiManager().refresh(ctx.getPlayer());
                    })
                    .build());
        }

        if (currentPage < maxPage) {
            setItem(GUIItem.builder()
                    .slot(getNextButtonSlot())
                    .itemStack(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, plugin)
                            .name("§aNext Page")
                            .lore("§8You are on page §7" + (currentPage + 1) + "/" + (maxPage + 1))
                            .build())
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .onClick(ClickType.LEFT, ctx -> {
                        currentPage++;
                        ctx.getGuiManager().refresh(ctx.getPlayer());
                    })
                    .build());
        }
    }

    protected void renderBackButton() {
        if (backFrame == null) return;

        setItem(GUIItem.builder()
                .slot(getBackButtonSlot())
                .itemStack(new ItemBuilder(Material.PAPER, plugin)
                        .name("§eBack")
                        .lore("§7Return to previous menu.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> ctx.getGuiManager().open(ctx.getPlayer(), backFrame))
                .build());
    }

    protected void renderBorder() {
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, plugin)
                .name(" ")
                .build();

        for (int i = 0; i < size; i++) {
            int row = i / 9;
            int col = i % 9;
            boolean isBorder = row == 0 || row == (size / 9 - 1) || col == 0 || col == 8;

            if (isBorder && getItemAtSlot(i) == null) {
                setItem(GUIItem.of(filler, i));
            }
        }
    }

    private int getMaxPage() {
        return Math.max((cachedElements.size() - 1) / itemsPerPage, 0);
    }

    protected abstract List<T> getElements();

    protected abstract GUIItem buildItem(T element, int slot);

    protected List<Integer> getContentSlots() {
        return List.of(
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34
        );
    }

    protected int getPreviousButtonSlot() {
        return size - 6;
    }

    protected int getNextButtonSlot() {
        return size - 4;
    }

    protected int getBackButtonSlot() {
        return size - 9; // usually bottom left slot
    }
}
