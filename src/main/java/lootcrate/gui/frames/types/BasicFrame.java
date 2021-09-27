package lootcrate.gui.frames.types;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUICloseEvent;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.items.GUIItem;
import lootcrate.gui.items.NavItems;
import lootcrate.objects.Crate;
import lootcrate.utils.ObjUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class BasicFrame implements Frame, Listener {
    protected LootCrate plugin;
    protected int id;
    protected Player player;
    protected String title;
    protected GUIItem[] contents;
    protected Inventory inventory;
    protected int size = 45;
    protected int usableSize = size-9;
    protected int page = 1;

    public BasicFrame(LootCrate plugin, Player p, String title, GUIItem[] contents, int size) {
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
        this.usableSize = usableSize = size-9;
    }

    public BasicFrame(LootCrate plugin, Player p, String title, GUIItem[] contents) {
        this.id = ObjUtils.randomID(5);
        this.plugin = plugin;
        this.player = p;
        this.title = title;

        if (contents.length != size)
            this.contents = new GUIItem[size];
        else
            this.contents = contents;

        this.inventory = createInventory();
    }

    public BasicFrame(LootCrate plugin, Player p, String title, int size) {
        this.id = ObjUtils.randomID(5);
        this.plugin = plugin;
        this.player = p;
        this.title = title;
        this.contents = new GUIItem[size];
        this.size = size;
        this.inventory = createInventory();
        this.usableSize = usableSize = size-9;
    }

    public BasicFrame(LootCrate plugin, Player p, String title) {
        this.id = ObjUtils.randomID(5);
        this.plugin = plugin;
        this.player = p;
        this.title = title;
        this.contents = new GUIItem[size];
        this.inventory = createInventory();
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
    public void generateNav() {
        for(int i = size-9; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, Material.RED_STAINED_GLASS_PANE, ""));
        }
        this.setItem(size-5, new GUIItem(size-5, NavItems.NAV_CLOSE));
        this.setItem(size-7, new GUIItem(size-7, NavItems.NAV_PREV));
        this.setItem(size-3, new GUIItem(size-3, NavItems.NAV_NEXT));
    }

    @Override
    public void nextPage(Crate crate) {
        if((page*usableSize) >= crate.getItems().size()) return;
        clearUsableItems();
        for(int i = 0; i < usableSize; i++) {
            if(usableSize+i >= crate.getItems().size()) break;
            this.setItem(i, new GUIItem(i, crate.getItems().get(usableSize+i)));
        }
        page++;
    }

    @Override
    public void previousPage(Crate crate) {
        if(page == 1) return;
        clearUsableItems();
        for(int i = 0; i < usableSize; i++) {
            if(usableSize-i >= crate.getItems().size()) break;
            if(crate.getItems().get(usableSize-i) == null) continue;
            this.setItem(i, new GUIItem(i, crate.getItems().get(usableSize-i)));
        }

        page--;

    }

    @Override
    public void clearUsableItems() {
        for(int i = 0; i < usableSize; i++) {
            //if(crate.getItems().get(usableSize+i) == null) continue;
            this.setItem(i, new GUIItem(i, Material.AIR));
        }
    }

    public boolean navCheck(GUIItemClickEvent e, Crate crate) {
        ItemStack item = this.getContents()[e.getItem().getSlot()].getItemStack();

        if(item.isSimilar(NavItems.NAV_NEXT)) {
            this.nextPage(crate);
            return true;
        }
        if(item.isSimilar(NavItems.NAV_PREV)) {
            this.previousPage(crate);
            return true;
        }
        if(item.isSimilar(NavItems.NAV_CLOSE)) {
            this.closeFrame(player, this);
            return true;
        }
        return false;
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

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (!e.getWhoClicked().equals(this.getViewer()))
            return;
        if (e.getInventory() != this.getInventory())
            return;
        if (e.getClickedInventory() != this.getInventory())
            return;
        if (e.getCurrentItem() == null)
            return;

        GUIItemClickEvent event = new GUIItemClickEvent(e, this);
        Bukkit.getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
    }




}
