package lootcrate.enums;

import org.bukkit.Material;

public enum SortType {
    CHANCE(Material.DIAMOND, CustomizationOption.CRATE_SORT_BY_CHANCE_NAME, CustomizationOption.CRATE_SORT_BY_CHANCE_LORE),
    NAME(Material.NAME_TAG, CustomizationOption.CRATE_SORT_BY_NAME_NAME, CustomizationOption.CRATE_SORT_BY_NAME_LORE),
    ID(Material.COMMAND_BLOCK, CustomizationOption.CRATE_SORT_BY_ID_NAME, CustomizationOption.CRATE_SORT_BY_ID_LORE),
    NONE(Material.BARRIER, CustomizationOption.CRATE_SORT_BY_NONE_NAME, CustomizationOption.CRATE_SORT_BY_NONE_LORE);

    private final Material itemStack;
    private final CustomizationOption name;
    private final CustomizationOption description;

    SortType(Material itemStack, CustomizationOption name, CustomizationOption description)
    {
        this.itemStack = itemStack;
        this.name = name;
        this.description = description;
    }

    public Material getItemStack() {
        return itemStack;
    }

    public CustomizationOption getName() {
        return name;
    }

    public CustomizationOption getDescription() {
        return description;
    }
}
