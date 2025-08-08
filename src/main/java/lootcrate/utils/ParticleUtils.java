package lootcrate.utils;

import org.bukkit.Particle;
import org.bukkit.Color;

public class ParticleUtils {
    public static Particle.DustOptions getDustOptions(String colorName) {
        Color color = getColor(colorName);
        if (color == null) return null;
        return new Particle.DustOptions(color, 1.0F);
    }

    private static Color getColor(String name) {
        if (name == null) return null;
        switch (name.toLowerCase()) {
            case "red": return Color.RED;
            case "green": return Color.LIME;
            case "blue": return Color.BLUE;
            case "yellow": return Color.YELLOW;
            case "aqua": return Color.AQUA;
            case "fuchsia": return Color.FUCHSIA;
            case "white": return Color.WHITE;
            case "black": return Color.BLACK;
            case "gray": return Color.GRAY;
            case "orange": return Color.ORANGE;
            case "purple": return Color.PURPLE;
            case "navy": return Color.NAVY;
            case "silver": return Color.SILVER;
            case "teal": return Color.TEAL;
            case "maroon": return Color.MAROON;
            case "olive": return Color.OLIVE;
            case "lime": return Color.LIME;
            case "none": return null;
            default: return null;
        }
    }
} 