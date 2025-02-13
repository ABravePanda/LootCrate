package lootcrate.enums;

import org.bukkit.Material;

public enum AnimationStyle {

    NONE(Material.BARRIER, CustomizationOption.CRATE_ANIMATION_NONE_NAME, CustomizationOption.CRATE_ANIMATION_NONE_LORE),
    CSGO(Material.BOW, CustomizationOption.CRATE_ANIMATION_CSGO_NAME, CustomizationOption.CRATE_ANIMATION_CSGO_LORE),
    RANDOM_GLASS(Material.ORANGE_STAINED_GLASS, CustomizationOption.CRATE_ANIMATION_RANDOM_GLASS_NAME, CustomizationOption.CRATE_ANIMATION_RANDOM_GLASS_LORE),
    REMOVING_ITEM(Material.TNT, CustomizationOption.CRATE_ANIMATION_REMOVING_ITEM_NAME, CustomizationOption.CRATE_ANIMATION_REMOVING_ITEM_LORE);

    private final Material itemStack;
    private final CustomizationOption name;
    private final CustomizationOption description;

    AnimationStyle(Material itemStack, CustomizationOption name, CustomizationOption description)
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
