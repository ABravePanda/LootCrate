package lootcrate.gui.frames.types;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUICloseEvent;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.items.GUIItem;
import lootcrate.utils.ObjUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public abstract class BasicFrame implements Frame, Listener {
    protected LootCrate plugin;
    protected int id;
    protected Player player;
    protected String title;
    protected GUIItem[] contents;
    protected Inventory inventory;
    protected int size = 45;

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
        plugin.getInvManager().closeFrame(p, frame);
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

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        if (!e.getPlayer().equals(this.getViewer()))
            return;
        if (e.getInventory() != this.getInventory())
            return;

        GUICloseEvent event = new GUICloseEvent(Bukkit.getPlayer(e.getPlayer().getUniqueId()), this);
        Bukkit.getPluginManager().callEvent(event);
    }

}
