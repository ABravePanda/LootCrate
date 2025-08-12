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

/**
 * Abstract GUI frame implementation for paginated content.
 * <p>
 * Extends {@link AbstractGuiFrame} and provides:
 * <ul>
 *   <li>Page navigation (next/previous buttons)</li>
 *   <li>Back button to return to another {@link GuiFrame}</li>
 *   <li>Border rendering</li>
 *   <li>Automated paging of a list of elements</li>
 * </ul>
 * <p>
 * Subclasses must implement:
 * <ul>
 *   <li>{@link #getElements()} — to provide the full list of items</li>
 *   <li>{@link #buildItem(Object, int)} — to convert an element into a {@link GUIItem}</li>
 * </ul>
 *
 * @param <T> the type of elements being paginated
 */
public abstract class PageableGuiFrame<T> extends AbstractGuiFrame {

    /** The currently displayed page index (0-based). */
    protected int currentPage = 0;

    /** Number of elements to display per page. */
    protected final int itemsPerPage;

    /** Cached list of all elements to display. */
    protected List<T> cachedElements = Collections.emptyList();


    /**
     * Creates a new paginated GUI frame.
     *
     * @param plugin        the owning plugin
     * @param size          the inventory size
     * @param title         the inventory title
     * @param viewer        the player viewing this GUI
     * @param itemsPerPage  the number of items per page
     */
    public PageableGuiFrame(JavaPlugin plugin, int size, String title, Player viewer, int itemsPerPage) {
        super(plugin, size, title, viewer);
        this.itemsPerPage = itemsPerPage;
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

    /**
     * Renders a specific page of elements into the content slots.
     *
     * @param page the page index (0-based)
     */
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

    /**
     * Renders navigation buttons for moving between pages.
     */
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

    /**
     * Renders a back button if a {@link #getBackframe()} is set.
     */
    protected void renderBackButton() {
        if (getBackframe() == null) return;

        setItem(GUIItem.builder()
                .slot(getBackButtonSlot())
                .itemStack(new ItemBuilder(Material.PAPER, plugin)
                        .name("§eBack")
                        .lore("§7Return to previous menu.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> ctx.getGuiManager().open(ctx.getPlayer(), getBackframe()))
                .build());
    }

    /**
     * Renders a decorative border around the content area.
     */
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

    /**
     * @return the maximum page index (0-based)
     */
    private int getMaxPage() {
        return Math.max((cachedElements.size() - 1) / itemsPerPage, 0);
    }

    /**
     * @return the complete list of elements to be displayed
     */
    protected abstract List<T> getElements();

    /**
     * Builds a {@link GUIItem} for a given element.
     *
     * @param element the element to display
     * @param slot    the target slot index
     * @return the built GUI item, or {@code null} for an empty slot
     */
    protected abstract GUIItem buildItem(T element, int slot);

    /**
     * @return the list of slot indices used for content items
     */
    protected List<Integer> getContentSlots() {
        return List.of(
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34
        );
    }

    /** @return the slot index for the "previous page" button. */
    protected int getPreviousButtonSlot() {
        return size - 6;
    }

    /** @return the slot index for the "next page" button. */
    protected int getNextButtonSlot() {
        return size - 4;
    }

    /** @return the slot index for the "back" button (default: bottom left). */
    protected int getBackButtonSlot() {
        return size - 9;
    }
}
