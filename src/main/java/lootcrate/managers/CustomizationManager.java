package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CustomizationManager extends BasicManager {
    private final String PREFIX = "custom-gui.";

    public CustomizationManager(LootCrate plugin) {
        super(plugin);
    }

    public String parseString(CustomizationOption option) {
        String msg = getStringOption(option);

        if (msg == null || msg.isEmpty())
            return ChatColor.RED + "Cannot find string {" + option.getKey() + "}";

        String colorMsg = ChatColor.translateAlternateColorCodes('&', msg);
        return colorMsg;
    }

    public Material parseMaterial(CustomizationOption option)
    {
        String text = getStringOption(option);
        try
        {
            Material.valueOf(text);
        }
        catch (IllegalArgumentException e)
        {
            return Material.BARRIER;
        }
        return Material.valueOf(text);
    }

    public long parseLong(CustomizationOption option)
    {
        String text = getStringOption(option);
        if (text == null || text.isEmpty())
            return 0l;
        return CommandUtils.tryParseLong(text);
    }

    public String getStringOption(CustomizationOption option)
    {
        return this.getPlugin().getConfig().getString(PREFIX + option.getKey());
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
