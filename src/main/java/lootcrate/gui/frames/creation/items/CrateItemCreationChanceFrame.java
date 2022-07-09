package lootcrate.gui.frames.creation.items;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CrateItemCreationChanceFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private CrateItem crateItem;

    public CrateItemCreationChanceFrame(LootCrate plugin, Player p, Crate crate, CrateItem item) {
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
        fillItems();
        fillInformation();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }



    private void fillItems()
    {

        //Add
        this.setItem(18, new GUIItem(18, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+10.0"));
        this.setItem(19, new GUIItem(19, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+1.0"));
        this.setItem(20, new GUIItem(20, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+0.1" ));
        this.setItem(21, new GUIItem(21, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+0.01" ));

        this.setItem(27, new GUIItem(27, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+50.0"));
        this.setItem(28, new GUIItem(28, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+5.0"));
        this.setItem(29, new GUIItem(29, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+0.5"));
        this.setItem(30, new GUIItem(30, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+0.05"));

        //Sub
        this.setItem(23, new GUIItem(23, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-0.01"));
        this.setItem(24, new GUIItem(24, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-0.1"));;
        this.setItem(25, new GUIItem(25, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-1.0"));
        this.setItem(26, new GUIItem(26, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-10.0"));

        this.setItem(32, new GUIItem(32, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-0.05"));
        this.setItem(33, new GUIItem(33, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-0.5"));
        this.setItem(34, new GUIItem(34, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-5.0"));
        this.setItem(35, new GUIItem(35, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-50.0"));
    }

    private void fillInformation()
    {
        this.setItem(4, new GUIItem(4, Material.BEACON, ChatColor.GREEN + "Chance", ChatColor.GRAY + "" + crateItem.getChance() + "%"));
    }

    private double getAmountFromItem(GUIItem item)
    {
        return Double.valueOf(item.getStrippedName().replace("+", "").replace("-", ""));
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if(item.getType() == Material.LIME_STAINED_GLASS_PANE || item.getType() == Material.GREEN_STAINED_GLASS_PANE)
        {
            crateItem.setChance(round(crateItem.getChance() + getAmountFromItem(e.getItem()), 2));
            fillInformation();
        }

        if(item.getType() == Material.PINK_STAINED_GLASS_PANE || item.getType() == Material.ORANGE_STAINED_GLASS_PANE)
        {
            if(crateItem.getChance() - getAmountFromItem(e.getItem()) < 0) return;
            crateItem.setChance(round(crateItem.getChance() - getAmountFromItem(e.getItem()), 2));
            fillInformation();
        }


    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateItemCreationFrame(plugin, player, crate, crateItem));
    }
}
