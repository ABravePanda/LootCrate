package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.AnimationStyle;
import lootcrate.enums.Option;
import org.bukkit.Sound;

public class OptionManager extends BasicManager {
    private final String PREFIX = "options.";

    /**
     * Constructor of OptionManager
     *
     * @param plugin Instance of plugin
     */
    public OptionManager(LootCrate plugin) {
        super(plugin);
    }

    public <T> T valueOf(Option option) {
        LootCrate plugin = this.getPlugin();
        switch (option.getType()) {
            case INTEGER:
                return (T) (Integer) plugin.getConfig().getInt(PREFIX + option.getKey());
            case BOOLEAN:
                return (T) (Boolean) plugin.getConfig().getBoolean(PREFIX + option.getKey());
            case DOUBLE:
                return (T) (Double) plugin.getConfig().getDouble(PREFIX + option.getKey());
            case LIST:
                return (T) plugin.getConfig().getList(PREFIX + option.getKey());
            case STRING:
                return (T) plugin.getConfig().getString(PREFIX + option.getKey());
            case MINECRAFT_SOUND:
                return (T) Sound.valueOf(plugin.getConfig().getString(PREFIX + option.getKey()));
            case ANIMATION_STYLE:
                return (T) AnimationStyle.valueOf(plugin.getConfig().getString(PREFIX + option.getKey()));
            default:
                return null;

        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
