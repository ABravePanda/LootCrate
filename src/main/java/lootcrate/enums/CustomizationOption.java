package lootcrate.enums;

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
    REMOVING_ANIMATION_DURATION("removing-animation-duration");



    String key;

    CustomizationOption(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
