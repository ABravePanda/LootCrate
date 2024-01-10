package lootcrate.objects;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown implements ConfigurationSerializable {
    private UUID uuid;
    private int crateId;
    private long lastOpenTimeMils;
    private int cooldownTime;

    public Cooldown(UUID uuid, int crateId, long lastOpenTimeMils, int cooldownTime) {
        this.uuid = uuid;
        this.crateId = crateId;
        this.lastOpenTimeMils = lastOpenTimeMils;
        this.cooldownTime = cooldownTime;
    }

    public Cooldown(Map<String, Object> data) {
        this.uuid = UUID.fromString((String) data.get("uuid"));
        this.crateId = (int) data.get("crateId");
        this.lastOpenTimeMils = (long) data.get("lastOpenTimeMils");
        this.cooldownTime = (int) data.get("cooldownTime");
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCrateId() {
        return crateId;
    }

    public long getLastOpenTimeMils() {
        return lastOpenTimeMils;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public boolean isOver() {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = lastOpenTimeMils + (cooldownTime * 1000L); // Convert cooldownTime to milliseconds
        return expirationTimeMillis <= currentTimeMillis;
    }

    public double getTimeLeft() {
        if (isOver()) return 0D;

        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = lastOpenTimeMils + (cooldownTime * 1000L); // Convert cooldownTime to milliseconds

        long timeLeftMillis = expirationTimeMillis - currentTimeMillis;
        double timeLeftSeconds = (double) timeLeftMillis / 1000.0; // Convert milliseconds to seconds

        return Math.round(timeLeftSeconds * 10.0) / 10.0; // Round to 1 decimal place
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("uuid", getUuid().toString());
        map.put("crateId", getCrateId());
        map.put("lastOpenTimeMils", getLastOpenTimeMils());
        map.put("cooldownTime", getCooldownTime());
        return map;
    }
}
