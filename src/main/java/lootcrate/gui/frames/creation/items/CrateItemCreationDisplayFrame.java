package lootcrate.gui.frames.creation.items;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.menu.option.CrateOptionMainMenuFrame;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CacheManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.CrateOption;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateItemCreationDisplayFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private CrateItem crateItem;
    public CrateItemCreationDisplayFrame(LootCrate plugin, Player p, Crate crate, CrateItem item) {
        super(plugin, p, "");

        this.plugin = plugin;
        this.crate = crate;
        this.crateItem = item;
        this.title = ChatColor.GREEN + "" + crateItem.getId();

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillOptions();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods

    public void fillBackground(Material m) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, m));
        }
    }

    public void fillOptions() {

        if(crateItem.isDisplay())
            this.setItem(13, new GUIItem(13, Material.NAME_TAG, ChatColor.GOLD + "Display Only",
                    ChatColor.GREEN + "True", "", ChatColor.GRAY + "If you want this reward", ChatColor.GRAY + "to only run commands", ChatColor.GRAY + "set this to " + ChatColor.GREEN + "TRUE"));
        else
            this.setItem(13, new GUIItem(13, Material.NAME_TAG, ChatColor.GOLD + "Display Only",
                    ChatColor.RED + "False", "", ChatColor.GRAY + "If you want this reward", ChatColor.GRAY + "to only run commands", ChatColor.GRAY + "set this to " + ChatColor.GREEN + "TRUE"));

        this.setItem(20, new GUIItem(20, Material.SLIME_BALL, ChatColor.GREEN + "True"));
        this.setItem(24, new GUIItem(24, Material.FIRE_CHARGE, ChatColor.RED + "False"));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        switch (item.getType()) {
            case SLIME_BALL:
                crateItem.setDisplay(true);
                plugin.getManager(CacheManager.class).update(crate);
                fillOptions();
                break;
            case FIRE_CHARGE:
                crateItem.setDisplay(false);
                plugin.getManager(CacheManager.class).update(crate);
                fillOptions();
                break;
            default:
                break;
        }

        e.setCancelled(true);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateItemCreationFrame(plugin, player, crate, crateItem));
        return;
    }
}
