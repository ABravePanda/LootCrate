package lootcrate.enums;

public enum FileType {

    CRATES("crates.yml"),
    LOCATIONS("locations.yml"),
    KEYS("player_keys.yml"),
    COOLDOWNS("cooldowns.yml"),
    DEBUG("debug.txt"),
    LOG("log.txt");

    private final String name;

    FileType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

}
