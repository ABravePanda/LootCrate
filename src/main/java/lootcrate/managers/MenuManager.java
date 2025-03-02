package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.gui.item.MenuItem;
import lootcrate.gui.menu.*;
import lootcrate.gui.menu.creation.MenuCreationSize;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager extends BasicManager {

    private final FileManager fileManager;
    private final Map<UUID, MenuCreationStage> menuCreationStages = new HashMap<UUID, MenuCreationStage>();
    private final Map<UUID, Menu> openMenuMap = new HashMap<>();

    public MenuManager(LootCrate plugin) {
        super(plugin);
        fileManager = plugin.getManager(FileManager.class);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public void openMenu(Player player, Menu menu) {
        closeMenu(player);

        openMenuMap.put(player.getUniqueId(), menu);
        menu.open(player);
    }

    public void closeMenu(Player player) {
        if(hasOpenMenu(player)) {
            Menu menu = openMenuMap.get(player.getUniqueId());
            menu.close(player);
            openMenuMap.remove(player.getUniqueId());
        }
    }

    public boolean hasOpenMenu(Player player) {
        return openMenuMap.containsKey(player.getUniqueId());
    }

    public void beginMenuCreationStage(Player player, int crateId) {
        if(menuCreationStages.containsKey(player.getUniqueId())) {
            menuCreationStages.remove(player.getUniqueId());
        }
        menuCreationStages.put(player.getUniqueId(), new MenuCreationStage(crateId));
        MenuCreationSize menuCreationSize = new MenuCreationSize(getPlugin());
        openMenu(player, menuCreationSize);
    }

    public MenuCreationStage getMenuCreationStage(Player player) {
        return menuCreationStages.get(player.getUniqueId());
    }

    public CustomMenu loadMenu(int crateId, MenuType menuType) {
        File menuDirectory = new File(fileManager.getPlugin().getDataFolder(), "menus");

        if (!menuDirectory.exists() || !menuDirectory.isDirectory()) {
            getPlugin().getLogger().severe("Menus directory not found!");
            return null;
        }

        // Find the matching menu file based on crateId and menuType
        File targetFile = null;
        for (File file : menuDirectory.listFiles()) {
            if (file.getName().startsWith(crateId + "_" + menuType.name()) && file.getName().endsWith(".yml")) {
                targetFile = file;
                break;
            }
        }

        // If no file is found, return null
        if (targetFile == null) {
            getPlugin().getLogger().severe("No menu file found for crateId: " + crateId + ", menuType: " + menuType.name());
            return null;
        }

        // Load the found file
        FileConfiguration configuration = fileManager.getConfiguration(targetFile);

        String name = configuration.getString("name", "Unknown Menu");

        // Load size safely
        String rowsString = configuration.getString("size", SimpleMenu.Rows.FIVE.name());
        SimpleMenu.Rows rows;
        try {
            rows = SimpleMenu.Rows.valueOf(rowsString);
        } catch (IllegalArgumentException e) {
            getPlugin().getLogger().severe("Invalid 'size' value in " + targetFile.getName() + ": " + rowsString);
            return null;
        }

        // Load animation type safely
        String animationTypeString = configuration.getString("animationType", AnimationType.STATIC.name());
        AnimationType animationType;
        try {
            animationType = AnimationType.valueOf(animationTypeString);
        } catch (IllegalArgumentException e) {
            getPlugin().getLogger().severe("Invalid 'animationType' value in " + targetFile.getName() + ": " + animationTypeString);
            return null;
        }

        // Create the menu
        CustomMenu customMenu = new CustomMenu(getPlugin(), menuType, animationType, crateId, rows, name);

        // Load slots
        if (configuration.contains("slots")) {
            for (String key : configuration.getConfigurationSection("slots").getKeys(false)) {
                int index = Integer.parseInt(key);

                // Load slot type safely
                String slotTypeString = configuration.getString("slots." + key + ".type");
                if (slotTypeString != null) {
                    try {
                        SlotType slotType = SlotType.valueOf(slotTypeString);
                        customMenu.setSlotType(index, slotType);
                    } catch (IllegalArgumentException e) {
                        getPlugin().getLogger().severe("Invalid slot type in " + targetFile.getName() + " at slot " + index);
                    }
                }

                // Load item if present
                if (configuration.contains("slots." + key + ".item")) {
                    ItemStack itemStack = configuration.getItemStack("slots." + key + ".item");
                    if (itemStack != null) {
                        customMenu.setItem(index, new MenuItem(itemStack));
                    }
                }
            }
        }

        return customMenu;
    }

    public File saveMenu(CustomMenu menu) {
        File file = fileManager.createFile("menus" + File.separator + menu.getCrateId() + "_" + menu.getMenuType().name() + ".yml");
        FileConfiguration configuration = fileManager.getConfiguration(file);

        configuration.set("name", "Test");
        configuration.set("crate", menu.getCrateId());
        configuration.set("menuType", menu.getMenuType().name());
        configuration.set("animationType", menu.getAnimationType().name());
        configuration.set("size", menu.getSize().toString());

        int index = 0;
        for(SlotType slotType : menu.getSlotTypes()) {
            MenuItem menuItem = menu.getItem(index);
            configuration.set("slots." + index + ".type", slotType.name());

            if(menuItem != null) {
                configuration.set("slots." + index + ".item", menuItem.getStack());
            }

            index++;
        }

        fileManager.saveFile(file, configuration);
        return file;
    }



    public class MenuCreationStage {
        private int crateId;
        private SimpleMenu.Rows size;
        private MenuType menuType;
        private AnimationType animationType;

        public MenuCreationStage(int crateId) {
            this.crateId = crateId;
        }


        public SimpleMenu.Rows getSize() {
            return size;
        }

        public void setSize(SimpleMenu.Rows size) {
            this.size = size;
        }

        public MenuType getMenuType() {
            return menuType;
        }

        public void setMenuType(MenuType menuType) {
            this.menuType = menuType;
        }

        public AnimationType getAnimationType() {
            return animationType;
        }

        public void setAnimationType(AnimationType animationType) {
            this.animationType = animationType;
        }

        public int getCrateId() {
            return crateId;
        }

        public CustomMenu createMenu(LootCrate plugin, String name) {
            return new CustomMenu(plugin, getMenuType(), getAnimationType(), getCrateId(), getSize(), name);
        }

        @Override
        public String toString() {
            return "MenuCreationStage{" +
                    "  crateId=" + crateId +
                    ", size=" + size +
                    ", menuType=" + menuType +
                    ", animationType=" + animationType +
                    '}';
        }
    }
}
