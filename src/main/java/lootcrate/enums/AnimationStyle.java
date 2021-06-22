package lootcrate.enums;

import org.bukkit.Material;

public enum AnimationStyle {

    NONE(Material.BARRIER, "None", "Instantly gives the item."),
    CSGO(Material.BOW, "CSGO", "Scrolling type animation."),
    RANDOM_GLASS(Material.ORANGE_STAINED_GLASS, "Random Glass", "Glass background randomly switches."),
    REMOVING_ITEM(Material.TNT, "Removing Item", "Removes an item until one left.");

    private final Material itemStack;
    private final String name;
    private final String[] description;

    AnimationStyle(Material itemStack, String name, String... description)
    {
        this.itemStack = itemStack;
        this.name = name;
        this.description = description;
    }

    public Material getItemStack() {
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public String[] getDescription() {
        return description;
    }
}
