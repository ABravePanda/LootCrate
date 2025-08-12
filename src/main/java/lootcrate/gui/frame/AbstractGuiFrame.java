package lootcrate.gui.frame;

import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Base implementation of the {@link GuiFrame} interface.
 * <p>
 * This class provides common state and utility methods for all GUI frames,
 * including:
 * <ul>
 *   <li>Tracking viewer, title, and size</li>
 *   <li>Managing {@link GUIItem} instances in slot-indexed storage</li>
 *   <li>Preventing closure via a ready-to-close flag</li>
 *   <li>Filling empty slots with default {@link GUIItem}s</li>
 * </ul>
 * <p>
 * Concrete frames should extend this class and implement {@link #render()} (and optionally {@link #tick()}).
 */
public abstract class AbstractGuiFrame implements GuiFrame {

    /** Unique identifier for this frame instance. */
    protected final UUID uuid;

    /** Reference to the hosting plugin, for access to schedulers/resources. */
    protected final JavaPlugin plugin;

    /** Number of slots in the inventory (must be a multiple of 9). */
    protected final int size;

    /** The display title for the inventory. */
    protected final String title;

    /** The player currently viewing the GUI. */
    protected final Player viewer;

    /** Map of slot index to {@link GUIItem}. */
    protected final Map<Integer, GUIItem> items = new HashMap<>();

    /** Whether the GUI can be closed by the player. */
    protected boolean readyToClose;

    protected GuiFrame backframe;

    /**
     * Creates a new {@code AbstractGuiFrame}.
     *
     * @param plugin the plugin that owns this GUI
     * @param size   the inventory size (must be a multiple of 9)
     * @param title  the inventory title
     * @param viewer the player viewing this GUI
     */
    public AbstractGuiFrame(JavaPlugin plugin, int size, String title, Player viewer) {
        this.uuid = UUID.randomUUID();
        this.plugin = plugin;
        this.size = size;
        this.title = title;
        this.viewer = viewer;
        fillEmptyGUIObjects();
        readyToClose = !preventsClose();
    }

    /**
     * Assigns a {@link GUIItem} to a specific slot.
     *
     * @param item the GUI item
     */
    protected void setItem(GUIItem item) {
        items.put(item.getSlot(), item);
    }

    /** Removes all GUI items from the frame. */
    public void clearItems() {
        items.clear();
    }

    /**
     * @return an unmodifiable collection of all current GUI items
     */
    public Collection<GUIItem> getItems() {
        return items.values();
    }

    public void setBackFrame(GuiFrame backframe) {
        this.backframe = backframe;
    }

    public GuiFrame getBackframe() {
        return backframe;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public Player getViewer() {
        return viewer;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public GUIItem getItemAtSlot(int slot) {
        return items.get(slot);
    }

    @Override
    public void setReadyToClose() {
        readyToClose = true;
    }

    @Override
    public void unsetReadyToClose() {
        readyToClose = false;
    }

    @Override
    public boolean preventsClose() {
        return !readyToClose;
    }

    @Override
    public void tick() {
        // Default: do nothing
    }

    /**
     * @return true if the GUI can be closed by the viewer
     */
    public boolean getReadyToClose() {
        return readyToClose;
    }

    /**
     * @return the plugin that owns this GUI
     */
    public JavaPlugin getJavaPlugin() {
        return plugin;
    }

    /**
     * Fills all empty slots with a default {@link GUIItem}
     * using {@link CancelPolicy#ALWAYS} and no visible item.
     */
    private void fillEmptyGUIObjects() {
        for (int i = 0; i < size; i++) {
            GUIItem guiItem = GUIItem.builder()
                    .slot(i)
                    .itemStack(null)
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .build();
            setItem(guiItem);
        }
    }
}
