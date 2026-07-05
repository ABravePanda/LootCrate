package lootcrate.enums;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;

import java.util.List;
import java.util.Objects;

public final class SoundKey {

    private final String key;

    private SoundKey(String key) {
        this.key = key;
    }

    public static SoundKey fromKey(String raw) {
        if (raw == null) return null;
        String normalized = raw.toLowerCase();
        NamespacedKey namespacedKey = NamespacedKey.fromString(normalized);
        if (namespacedKey == null) return null;
        Sound sound = Registry.SOUNDS.get(namespacedKey);
        if (sound == null) return null;
        return new SoundKey(normalized);
    }

    public static List<SoundKey> values() {
        return Registry.SOUNDS.stream()
                .map(sound -> new SoundKey(sound.getKey().getKey()))
                .sorted((a, b) -> a.key.compareTo(b.key))
                .toList();
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SoundKey other)) return false;
        return key.equals(other.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}
