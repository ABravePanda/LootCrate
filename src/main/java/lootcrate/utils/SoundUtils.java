package lootcrate.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;

public class SoundUtils {

    public static Sound valueOf(String sound) {
        sound = sound.toLowerCase();
        try {
            NamespacedKey key = NamespacedKey.minecraft(sound);
            return Registry.SOUNDS.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return Sound.ENTITY_FIREWORK_ROCKET_LAUNCH;
        }
    }
}
