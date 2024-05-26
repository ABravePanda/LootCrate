package lootcrate.gui;

import lootcrate.LootCrate;

import java.util.UUID;

public abstract class AbstractNavigableFrame extends AbstractFrame implements Navigable {


    public AbstractNavigableFrame(LootCrate plugin, int size, String title, UUID owner) {
        super(plugin, size, title, owner);
    }

    @Override
    public void init() {
        super.init();
        createNavbar();
    }

    @Override
    public void createNavbar() {
        //TODO Add in navigation bar
    }
}
