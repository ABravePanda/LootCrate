package lootcrate.gui.frames.types;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUICloseEvent;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.items.GUIItem;
import lootcrate.gui.items.NavItems;
import lootcrate.utils.ObjUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFrame implements Frame, Listener, Pageable {
    protected LootCrate plugin;
    protected int id;
    protected Player player;
    protected String title;
    protected GUIItem[] contents;
    protected Inventory inventory;
    protected int size = 45;
    protected int usableSize = size - 9;
    protected int page = 1;
    protected NavItems navItems;

    public BaseFrame(LootCrate plugin, Player p, String title, GUIItem[] contents, int size) {
        this.id = ObjUtils.randomID(5);
        this.plugin = plugin;
        this.player = p;
        this.title = title;
        this.size = size;

        if (contents.length != size)
            this.contents = new GUIItem[size];
        else
            this.contents = contents;

        this.inventory = createInventory();
        this.usableSize = usableSize = size - 9;
        navItems = new NavItems(plugin);
    }

    public BaseFrame(LootCrate plugin, Player p, String title, GUIItem[] contents) {
        this.id = ObjUtils.randomID(5);
        this.plugin = plugin;
        this.player = p;
        this.title = title;

        if (contents.length != size)
            this.contents = new GUIItem[size];
        else
            this.contents = contents;

        this.inventory = createInventory();
        navItems = new NavItems(plugin);
    }

    public BaseFrame(LootCrate plugin, Player p, String title, int size) {
        this.id = ObjUtils.randomID(5);
        this.plugin = plugin;
        this.player = p;
        this.title = title;
        this.contents = new GUIItem[size];
        this.size = size;
        this.inventory = createInventory();
        this.usableSize = usableSize = size - 9;
        navItems = new NavItems(plugin);
    }

    public BaseFrame(LootCrate plugin, Player p, String title) {
        this.id = ObjUtils.randomID(5);
        this.plugin = plugin;
        this.player = p;
        this.title = title;
        this.contents = new GUIItem[size];
        this.inventory = createInventory();
        navItems = new NavItems(plugin);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Player getViewer() {
        return this.player;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public GUIItem[] getContents() {
        return this.contents;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getUsableSize() {
        return this.usableSize;
    }

    @Override
    public void open() {
        plugin.getInvManager().openFrame(player, this);
    }

    @Override
    public void close() {
        plugin.getInvManager().closeFrame(player, this);
    }

    @Override
    public void registerItems() {
        for (GUIItem item : getContents())
            if (item != null)
                plugin.getServer().getPluginManager().registerEvents(item, plugin);
    }

    @Override
    public void registerFrame() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void fillBackground(Material m) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, m, " "));
        }
    }


    @Override
    public void clearUsableItems() {
        for (int i = 0; i < usableSize; i++) {
            //if(crate.getItems().get(usableSize+i) == null) continue;
            this.setItem(i, new GUIItem(i, Material.AIR));
        }
    }

    @Override
    public List<GUIItem> getUsableItems() {
        List<GUIItem> items = new ArrayList<GUIItem>();
        for (int i = 0; i < usableSize; i++) {
            if (getInventory().getItem(i) == null || getInventory().getItem(i).getType() == Material.AIR) continue;
            items.add(new GUIItem(i, getInventory().getItem(i)));
        }

        return items;
    }

    public abstract void generateFrame();

    public void setItem(int slot, GUIItem item) {
        if (slot >= contents.length || slot < 0)
            return;

        contents[slot] = item;
        getInventory().setItem(slot, item.getItemStack());
    }

    public void openFrame(Player p, Frame frame) {
        plugin.getInvManager().openFrame(p, frame);
    }

    public void closeFrame(Player p, Frame frame) {
        GUICloseEvent event = new GUICloseEvent(p, frame);
        Bukkit.getPluginManager().callEvent(event);
    }

    public Inventory createInventory() {
        return Bukkit.createInventory(null, size, getTitle());
    }

    @Override
    public void generateNavigation() {

        this.setItem(getSize() - 1, new GUIItem(getSize() - 1, navItems.getNavBlocker()));
        this.setItem(getSize() - 2, new GUIItem(getSize() - 2, navItems.getNavBlocker()));
        this.setItem(getSize() - 3, new GUIItem(getSize() - 3, navItems.getNavNext()));
        this.setItem(getSize() - 4, new GUIItem(getSize() - 4, navItems.getNavBlocker()));
        this.setItem(getSize() - 5, new GUIItem(getSize() - 5, navItems.getNavClose()));
        this.setItem(getSize() - 6, new GUIItem(getSize() - 6, navItems.getNavBlocker()));
        this.setItem(getSize() - 7, new GUIItem(getSize() - 7, navItems.getNavPrev()));
        this.setItem(getSize() - 8, new GUIItem(getSize() - 8, navItems.getNavBlocker()));
        this.setItem(getSize() - 9, new GUIItem(getSize() - 9, navItems.getNavBlocker()));
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (!e.getWhoClicked().equals(this.getViewer()))
            return;
        if (e.getInventory() != this.getInventory())
            return;
        e.setCancelled(true);
    }


    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {

        if (!e.getWhoClicked().equals(this.getViewer()))
            return;

        if (e.getInventory() != this.getInventory())
            return;

        if (e.getClickedInventory() != this.getInventory()) {
            if (!(this instanceof InputAllowed))
                e.setCancelled(true);
            return;
        }

        if (e.getCurrentItem() == null) {
            if (!(this instanceof InputAllowed))
                e.setCancelled(true);
            return;
        }


        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && !(this instanceof ShiftClickAllowed)) {
            e.setCancelled(true);
            return;
        }

        //override the guiclick
        if (e.getCurrentItem().equals(navItems.getNavBlocker())) {
            e.setCancelled(true);
            return;
        }

        if (e.getCurrentItem().equals(navItems.getNavClose())) {
            e.setCancelled(true);
            player.closeInventory();
            return;
        }

        if (e.getCurrentItem().equals(navItems.getNavNext())) {
            e.setCancelled(true);
            nextPage();
            return;
        }

        if (e.getCurrentItem().equals(navItems.getNavPrev())) {
            e.setCancelled(true);
            previousPage();
            return;
        }

        GUIItemClickEvent event = new GUIItemClickEvent(e, this);
        Bukkit.getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
    }


}
