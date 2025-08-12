package lootcrate.enums;

public enum CrateOptionType {
    DISPLAY_CHANCES(
            "Display-Chances",
            DataType.BOOLEAN,
            "Whether the GUI should display the chance of each reward being won in the GUI."
    ),
    KNOCK_BACK(
            "Knockback",
            DataType.DOUBLE,
            "Amount of knockback applied to the player when opening the crate without the correct key."
    ),
    OPEN_SOUND(
            "Open-Sound",
            DataType.STRING,
            "The sound effect to play when the crate is opened."
    ),
    SOUND_VOLUME(
            "Sound-Volume",
            DataType.INTEGER,
            "Volume level of the crate opening sound."
    ),
    OPEN_MESSAGE(
            "Open-Message",
            DataType.STRING,
            "Message sent to the player upon opening the crate."
    ),
    HOLOGRAM_ENABLED(
            "Hologram-Enabled",
            DataType.BOOLEAN,
            "Whether a hologram should be displayed above the crate."
    ),
    HOLOGRAM_LINES(
            "Hologram-Lines",
            DataType.LIST,
            "List of text lines displayed in the hologram."
    ),
    HOLOGRAM_OFFSET_X(
            "Hologram-Offset-X",
            DataType.BOOLEAN,
            "Horizontal (X-axis) offset of the hologram from the crate."
    ),
    HOLOGRAM_OFFSET_Y(
            "Hologram-Offset-Y",
            DataType.BOOLEAN,
            "Vertical (Y-axis) offset of the hologram from the crate."
    ),
    HOLOGRAM_OFFSET_Z(
            "Hologram-Offset-Z",
            DataType.BOOLEAN,
            "Depth (Z-axis) offset of the hologram from the crate."
    ),
    ANIMATION_STYLE(
            "Animation-Style",
            DataType.STRING,
            "Name of the animation style used when the crate is opened."
    ),
    SORT_TYPE(
            "Sort-Type",
            DataType.STRING,
            "How rewards should be sorted in the display."
    ),
    COOLDOWN(
            "Cooldown",
            DataType.INTEGER,
            "Time in seconds before the crate can be opened again by the same player."
    );

    private final String key;
    private final DataType type;
    private final String description;

    CrateOptionType(String key, DataType type, String description) {
        this.key = key;
        this.type = type;
        this.description = description;
    }

    public static CrateOptionType fromKey(String key) {
        for (CrateOptionType value : CrateOptionType.values()) {
            if (key.equals(value.getKey()))
                return value;
        }
        return null;
    }

    public String getKey() {
        return this.key;
    }

    public DataType getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return key;
    }
}
