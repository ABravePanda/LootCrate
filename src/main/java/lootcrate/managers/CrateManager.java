package lootcrate.managers;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.*;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.RandomCollection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CrateManager extends BasicManager {

    /**
     * Constructor for CrateManager
     *
     * @param plugin An instance of the plugin
     */
    public CrateManager(LootCrate plugin) {
        super(plugin);
    }

    /**
     * Retrieves a crate item within the crate with the id
     *
     * @param crate The crate to be searched
     * @param id    The id to be compared with
     * @return The crate with the same id, or null
     */
    public CrateItem getCrateItemById(Crate crate, int id) {
        for (CrateItem item : crate.getItems()) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    /**
     * Retrieves a random CrateItem from the specified crate
     *
     * @param crate The crate wishing to be searched
     * @return A random CrateItem from the crate
     */
    public CrateItem getRandomItem(Crate crate) {
        RandomCollection<String> items = new RandomCollection<String>();

        for (CrateItem item : new ArrayList<>(crate.getItems()))
            items.add(item.getChance(), item);
        return items.next();
    }

    /**
     * Gets a random amount from a crate item
     *
     * @param item The CrateItem whos amount you want
     * @return A random amount between getMinAmount() and getMaxAmount()
     * @see CrateItem#getMaxAmount()
     * @see CrateItem#getMinAmount()
     */
    public int getRandomAmount(CrateItem item) {
        if (item.getMaxAmount() < item.getMinAmount())
            return 1;
        if(item.getMaxAmount() == item.getMinAmount())
            return item.getMinAmount();
        return ThreadLocalRandom.current().nextInt(item.getMinAmount(), item.getMaxAmount() + 1);
    }

    /**
     * Plays crate sound and sends message to player to open crate for
     *
     * @param crate Crate to open
     * @param p Player to open crate for
     */
    public void crateOpenEffects(Crate crate, Player p) {
        // play sound
        if (crate.getOption(CrateOptionType.OPEN_SOUND).getValue() != null) {
            if (crate.getOption(CrateOptionType.OPEN_SOUND).getValue().toString().equalsIgnoreCase("none"))
                return;
            if (Sound.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue()) == null)
                return;
            int soundVolume = 1;
            if (crate.getOption(CrateOptionType.SOUND_VOLUME) != null)
                soundVolume = (int) crate.getOption(CrateOptionType.SOUND_VOLUME).getValue();
            p.playSound(p.getLocation(), Sound.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue()),
                    soundVolume, 1);
        }

        // get message, send it
        if (crate.getOption(CrateOptionType.OPEN_MESSAGE).getValue() != null) {
            if (crate.getOption(CrateOptionType.OPEN_MESSAGE).getValue().toString().equalsIgnoreCase("none"))
                return;
            p.sendMessage(this.getPlugin().getManager(MessageManager.class).getPrefix()
                    + ChatColor.translateAlternateColorCodes('&', crate.getOption(CrateOptionType.OPEN_MESSAGE)
                    .getValue().toString().replace("{crate_name}", crate.getName())));
        }

    }

    public void giveReward(CrateItem crateItem, Player p) {
        int rnd = this.getPlugin().getManager(CrateManager.class).getRandomAmount(crateItem);

        if (!crateItem.isDisplay()) {
            for (int i = 0; i < rnd; i++)
                p.getInventory().addItem(crateItem.getItem());
        }

        int i = 1;

        for (String cmd : crateItem.getCommands()) {
            if (this.getPlugin().getManager(OptionManager.class).valueOf(Option.DISPATCH_COMMAND_ITEM_AMOUNT))
                i = rnd;
            for (int j = 0; j < i; j++)
                Bukkit.dispatchCommand(this.getPlugin().getServer().getConsoleSender(), cmd.replace("{player}", p.getName()).replace("{amount}", rnd + ""));
        }

    }

    public void addDefaultOptions(Crate crate) {

        String[] lines =
                {"{crate_name}", "&8Right-Click&7 to Unlock", "&8Left-Click&7 to View"};

        crate.addOption(CrateOptionType.KNOCK_BACK, 1.0D);
        crate.addOption(CrateOptionType.COOLDOWN, 0);
        crate.addOption(CrateOptionType.ANIMATION_STYLE, AnimationStyle.RANDOM_GLASS.toString());
        crate.addOption(CrateOptionType.SORT_TYPE, SortType.CHANCE.toString());
        crate.addOption(CrateOptionType.DISPLAY_CHANCES, true);
        crate.addOption(CrateOptionType.OPEN_SOUND, Sound.UI_TOAST_CHALLENGE_COMPLETE.toString());
        crate.addOption(CrateOptionType.OPEN_MESSAGE, "&fYou have opened &e{crate_name}&f.");
        crate.addOption(CrateOptionType.HOLOGRAM_ENABLED, true);
        crate.addOption(CrateOptionType.HOLOGRAM_LINES, Arrays.asList(lines));
        crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_X, 0.5D);
        crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Y, 1.8D);
        crate.addOption(CrateOptionType.HOLOGRAM_OFFSET_Z, 0.5D);
    }

    public Crate getCrateFromItemID(int id)
    {
        List<Crate> crates = this.getPlugin().getManager(CacheManager.class).getCache();
        for(Crate crate : crates)
            for(CrateItem item : crate.getItems())
                if(item.getId() == id) return crate;
        return null;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
