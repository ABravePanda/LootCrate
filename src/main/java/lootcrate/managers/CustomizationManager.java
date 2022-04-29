package lootcrate.managers;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.objects.Crate;
import lootcrate.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.regex.Matcher;

public class CustomizationManager extends BasicManager implements Manager {
    private final String PREFIX = "custom-gui.";

    public CustomizationManager(LootCrate plugin) {
        super(plugin);
    }

    public String parseName(CustomizationOption option) {
        String msg = getStringOption(option);

        if (msg == null || msg.isEmpty())
            return ChatColor.RED + "Cannot find string {" + option.getKey() + "}";

        String colorMsg = ChatColor.translateAlternateColorCodes('&', msg);
        return colorMsg;
    }

    public Material parseMaterial(CustomizationOption option)
    {
        String text = getStringOption(option);
        if(Material.valueOf(text) != null)
            return Material.valueOf(text);
        else return Material.BARRIER;
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
