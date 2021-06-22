package lootcrate.enums;

public enum FileType {

    CRATES("crates.yml"),
    LOCATIONS("locations.yml"),
    LOG("log.yml");

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
