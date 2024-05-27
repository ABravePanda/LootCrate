package lootcrate.gui;

import lootcrate.LootCrate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.UUID;

public abstract class AbstractFrame implements Frame {

    private LootCrate plugin;
    private int size;
    private String title;
    private UUID owner;
    private UUID id;
    private Inventory inventory;
    private GUIItem[] items;

    public AbstractFrame(LootCrate plugin, int size, String title, UUID owner) {
        this.plugin = plugin;
        this.id = UUID.randomUUID();
        this.size = size;
        this.title = title;
        this.owner = owner;
        this.items = new GUIItem[size];
        checkSize();
    }

    private void checkSize() {
        if(this.size % 9 != 0) {
            throw new IllegalStateException("Inventory size must be divisible by 9");
        }
    }

    @Override
    public void init() {
        this.inventory = Bukkit.createInventory(null, size, title);
        if(hasNavigation())
            initNavigation();
    }

    @Override
    public void initNavigation() {

    }

    @Override
    public GUIItem getItem(int slot) {
        return getItems()[slot];
    }

    @Override
    public void setItem(int slot, GUIItem guiItem) {
        items[slot] = guiItem;

        if(inventory != null) {
            inventory.setItem(slot, guiItem.getItemStack());
        }
    }

    @Override
    public LootCrate getPlugin() {
        return plugin;
    }


    @Override
    public GUIItem[] getItems() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean canBeManuallyClosed() {
        return false;
    }

    @Override
    public boolean readyToClose() {
        return false;
    }
}
