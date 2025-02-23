package lootcrate.utils;

import lootcrate.LootCrate;
import lootcrate.enums.FileType;
import lootcrate.enums.Sounds;
import lootcrate.managers.FileManager;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;

public class SoundUtils {

    public static Sounds valueOf(String sound) {
        sound = sound.toLowerCase();
        return Sounds.fromKey(sound);
    }

    public static void playSound(Player player, Sounds sound, int volume, int pitch) {
        try {
            player.playSound(player.getLocation(), sound.getKey(), volume, pitch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void debugPrintSounds(LootCrate plugin) {
        List<Sound> sounds = Registry.SOUNDS.stream().toList();
        FileManager fileManager = plugin.getManager(FileManager.class);

        File debugFile = fileManager.createFile(FileType.DEBUG);

        try (FileOutputStream fos = new FileOutputStream(debugFile);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter writer = new BufferedWriter(osw)) {

            for (Sound sound : sounds) {
                String soundName = sound.name().replace(".", "__");
                String soundValue = sound.getKeyOrNull().getKey();
                writer.write(soundName + "(\"" + soundValue + "\"),");  // Assuming Sound has a meaningful toString() method
                writer.newLine(); // Adds a newline for readability
            }

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }
}
