package lootcrate.utils;

import lootcrate.enums.SoundKey;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static SoundKey valueOf(String sound) {
        if (sound == null) return null;
        return SoundKey.fromKey(sound.toLowerCase());
    }

    public static void playSound(Player player, SoundKey sound, int volume, int pitch) {
        if (sound == null) return;
        try {
            player.playSound(player.getLocation(), sound.getKey(), volume, pitch);
        } catch (Exception ignored) {}
    }
}
