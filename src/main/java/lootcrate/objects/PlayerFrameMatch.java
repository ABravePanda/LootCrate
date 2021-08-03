package lootcrate.objects;

import java.util.UUID;

public class PlayerFrameMatch {

    private final UUID uuid;
    private final int frameid;

    public PlayerFrameMatch(UUID uuid, int frameid)
    {
        this.uuid = uuid;
        this.frameid = frameid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getFrameid() {
        return frameid;
    }

}
