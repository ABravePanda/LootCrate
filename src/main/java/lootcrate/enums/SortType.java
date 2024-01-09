package lootcrate.enums;

import org.bukkit.Material;

public enum SortType {
    CHANCE(Material.DIAMOND, "Chance", "The percent value the player will get the item over other items."),
    NAME(Material.NAME_TAG, "Name", "The display name or the items base name."),
    ID(Material.COMMAND_BLOCK, "ID", "The random ID the item is assigned."),
    NONE(Material.BARRIER, "None", "No real sorting method.");

    private final Material itemStack;
    private final String name;
    private final String[] description;

    SortType(Material itemStack, String name, String... description)
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
