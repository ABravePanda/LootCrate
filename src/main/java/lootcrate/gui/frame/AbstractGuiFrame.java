package lootcrate.gui.frame;

import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractGuiFrame implements GuiFrame {

    protected final UUID uuid;
    protected final JavaPlugin plugin;
    protected final int size;
    protected final String title;
    protected final Player viewer;
    protected final Map<Integer, GUIItem> items = new HashMap<>();

    protected boolean readyToClose;

    public AbstractGuiFrame(JavaPlugin plugin, int size, String title, Player viewer) {
        this.uuid = UUID.randomUUID();
        this.plugin = plugin;
        this.size = size;
        this.title = title;
        this.viewer = viewer;
        fillEmptyGUIObjects();
        readyToClose = !preventsClose();
    }

    protected void setItem(GUIItem item) {
        items.put(item.getSlot(), item);
    }

    public void clearItems() {
        items.clear();
    }

    public Collection<GUIItem> getItems() {
        return items.values();
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

    }

    public boolean getReadyToClose() {
        return readyToClose;
    }

    public JavaPlugin getJavaPlugin() {
        return plugin;
    }

    private void fillEmptyGUIObjects() {
        for(int i = 0; i < size; i++) {
            GUIItem guiItem = GUIItem.builder()
                    .slot(i)
                    .itemStack(null)
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .build();
            setItem(guiItem);
        }
    }
}

