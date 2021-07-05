package lootcrate.managers;

import lootcrate.LootCrate;

public abstract class BasicManager implements Manager {

    private final LootCrate plugin;

    public BasicManager(LootCrate plugin) {
        this.plugin = plugin;
    }

    public LootCrate getPlugin() {
        return plugin;
    }
}
