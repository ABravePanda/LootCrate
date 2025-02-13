package lootcrate.enums;

import org.checkerframework.checker.units.qual.min;

public enum CustomizationOption {
    NAVIGATION_BLOCKER_MATERIAL("navigation-blocker-material"),
    NAVIGATION_BLOCKER_NAME("navigation-blocker-name"),
    NAVIGATION_CLOSE_MATERIAL("navigation-close-material"),
    NAVIGATION_CLOSE_NAME("navigation-close-name"),
    NAVIGATION_NEXT_MATERIAL("navigation-next-material"),
    NAVIGATION_NEXT_NAME("navigation-next-name"),
    NAVIGATION_PREVIOUS_MATERIAL("navigation-previous-material"),
    NAVIGATION_PREVIOUS_NAME("navigation-previous-name"),
    CSGO_ANIMATION_POINTER_MATERIAL("csgo-animation-pointer-material"),
    CSGO_ANIMATION_POINTER_NAME("csgo-animation-pointer-name"),
    CSGO_ANIMATION_BACKGROUND_MATERIAL("csgo-animation-background-material"),
    CSGO_ANIMATION_BACKGROUND_NAME("csgo-animation-background-name"),
    CSGO_ANIMATION_WINNER_BACKGROUND_MATERIAL("csgo-animation-winner-background-material"),
    CSGO_ANIMATION_WINNER_BACKGROUND_NAME("csgo-animation-winner-background-name"),
    CSGO_ANIMATION_SCROLL_SPEED("csgo-animation-scroll-speed"),
    CSGO_ANIMATION_DURATION("csgo-animation-duration"),
    RND_ANIMATION_POINTER_MATERIAL("rnd-animation-pointer-material"),
    RND_ANIMATION_POINTER_NAME("rnd-animation-pointer-name"),
    RND_ANIMATION_GLASS_NAME("rnd-animation-glass-name"),
    RND_ANIMATION_WINNER_BACKGROUND_MATERIAL("rnd-animation-winner-background-material"),
    RND_ANIMATION_WINNER_BACKGROUND_NAME("rnd-animation-winner-background-name"),
    RND_ANIMATION_SCROLL_SPEED("rnd-animation-scroll-speed"),
    RND_ANIMATION_GLASS_SPEED("rnd-animation-glass-speed"),
    RND_ANIMATION_DURATION("rnd-animation-duration"),
    REMOVING_ANIMATION_FILLER_MATERIAL("removing-animation-filler-material"),
    REMOVING_ANIMATION_FILLER_NAME("removing-animation-filler-name"),
    REMOVING_ANIMATION_WINNER_BACKGROUND_MATERIAL("removing-animation-winner-background-material"),
    REMOVING_ANIMATION_WINNER_BACKGROUND_NAME("removing-animation-winner-background-name"),
    REMOVING_ANIMATION_DURATION("removing-animation-duration"),
    CLAIM_MENU_CLAIMALL_MATERIAL("claim-all-material"),
    CLAIM_MENU_CLAIMALL_NAME("claim-all-name"),

    CRATES_MAIN_MENU_TITLE("crates-main-menu-title"),
    ALL_CRATES_MENU_TITLE("all-crates-menu-title"),

    MAIN_CREATE_NEW_CRATE("main-create-new-crate"),
    MAIN_VIEW_ALL_CRATES("main-view-all-crates"),

    CRATE_VIEW_ITEMS_NAME("crate-view-items-name"),
    CRATE_VIEW_ITEMS_LORE("crate-view-items-lore"),
    CRATE_VIEW_KEY_NAME("crate-view-key-name"),
    CRATE_VIEW_KEY_LORE("crate-view-key-lore"),
    CRATE_VIEW_LOCATIONS_NAME("crate-view-locations-name"),
    CRATE_VIEW_LOCATIONS_LORE("crate-view-locations-lore"),
    CRATE_VIEW_OPTIONS_NAME("crate-view-options-name"),
    CRATE_VIEW_OPTIONS_LORE("crate-view-options-lore"),

    CRATE_ITEM_INFO_ID("crate-item-info-id"),
    CRATE_ITEM_INFO_CHANCE("crate-item-info-chance"),
    CRATE_ITEM_INFO_MIN("crate-item-info-min"),
    CRATE_ITEM_INFO_MAX("crate-item-info-max"),
    CRATE_ITEM_INFO_DISPLAY("crate-item-info-display"),
    CRATE_ITEM_INFO_COMMANDS("crate-item-info-commands"),
    CRATE_ITEM_COMMAND_FORMAT("crate-item-command-format"),
    CRATE_ITEM_LEFT_CLICK_EDIT("crate-item-left-click-edit"),

    CRATE_OPTIONS_NAME_NAME("crate-options-name-name"),
    CRATE_OPTIONS_NAME_LORE("crate-options-name-lore"),
    CRATE_OPTIONS_MESSAGE_NAME("crate-options-message-name"),
    CRATE_OPTIONS_MESSAGE_LORE("crate-options-message-lore"),
    CRATE_OPTIONS_COOLDOWN_NAME("crate-options-cooldown-name"),
    CRATE_OPTIONS_COOLDOWN_LORE("crate-options-cooldown-lore"),
    CRATE_OPTIONS_KNOCKBACK_NAME("crate-options-knockback-name"),
    CRATE_OPTIONS_KNOCKBACK_LORE("crate-options-knockback-lore"),
    CRATE_OPTIONS_SORT_NAME("crate-options-sort-name"),
    CRATE_OPTIONS_SORT_LORE("crate-options-sort-lore"),
    CRATE_OPTIONS_OPENSOUND_NAME("crate-options-opensound-name"),
    CRATE_OPTIONS_OPENSOUND_LORE("crate-options-opensound-lore"),
    CRATE_OPTIONS_HOLOGRAM_NAME("crate-options-hologram-name"),
    CRATE_OPTIONS_HOLOGRAM_LORE("crate-options-hologram-lore"),
    CRATE_OPTIONS_ANIMATION_NAME("crate-options-animation-name"),
    CRATE_OPTIONS_ANIMATION_LORE("crate-options-animation-lore"),

    CRATE_SORT_BY_NONE_NAME("crate-sort-by-none-name"),
    CRATE_SORT_BY_NONE_LORE("crate-sort-by-none-lore"),
    CRATE_SORT_BY_CHANCE_NAME("crate-sort-by-chance-name"),
    CRATE_SORT_BY_CHANCE_LORE("crate-sort-by-chance-lore"),
    CRATE_SORT_BY_NAME_NAME("crate-sort-by-name-name"),
    CRATE_SORT_BY_NAME_LORE("crate-sort-by-name-lore"),
    CRATE_SORT_BY_ID_NAME("crate-sort-by-id-name"),
    CRATE_SORT_BY_ID_LORE("crate-sort-by-id-lore"),

    CRATE_SOUND_LEFT_CLICK_ACTION("crate-sound-left-click-action"),
    CRATE_SOUND_SHIFT_LEFT_CLICK_ACTION("crate-sound-shift-left-click-action"),

    CRATE_HOLOGRAM_VISIBLE_NAME("crate-hologram-visible-name"),
    CRATE_HOLOGRAM_ENABLED("crate-hologram-enabled"),
    CRATE_HOLOGRAM_DISABLED("crate-hologram-disabled"),
    CRATE_HOLOGRAM_ENABLE("crate-hologram-enable"),
    CRATE_HOLOGRAM_DISABLE("crate-hologram-disable"),

    CRATE_ANIMATION_REMOVING_ITEM_NAME("crate-animation-removing-item-name"),
    CRATE_ANIMATION_REMOVING_ITEM_LORE("crate-animation-removing-item-lore"),
    CRATE_ANIMATION_NONE_NAME("crate-animation-none-name"),
    CRATE_ANIMATION_NONE_LORE("crate-animation-none-lore"),
    CRATE_ANIMATION_RANDOM_GLASS_NAME("crate-animation-random-glass-name"),
    CRATE_ANIMATION_RANDOM_GLASS_LORE("crate-animation-random-glass-lore"),
    CRATE_ANIMATION_CSGO_NAME("crate-animation-csgo-name"),
    CRATE_ANIMATION_CSGO_LORE("crate-animation-csgo-lore");


    String key;

    CustomizationOption(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
