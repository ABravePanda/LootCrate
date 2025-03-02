package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.FileType;
import lootcrate.gui.menu.CustomMenu;
import lootcrate.gui.menu.SimpleMenu;

public class MenuManager extends BasicManager {

    private final FileManager fileManager;

    public MenuManager(LootCrate plugin) {
        super(plugin);
        fileManager = plugin.getManager(FileManager.class);
        fileManager.createFile(FileType.DEBUG
    }

    public CustomMenu loadMenu() {

        CustomMenu customMenu = new CustomMenu(getPlugin(),
    }

    public void saveMenu() {

    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
