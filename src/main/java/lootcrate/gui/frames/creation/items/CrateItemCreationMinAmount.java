package lootcrate.gui.frames.creation.items;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
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

public class CrateItemCreationMinAmount extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private CrateItem crateItem;
    public CrateItemCreationMinAmount(LootCrate plugin, Player p, Crate crate, CrateItem item) {
        super(plugin, p, ChatColor.GREEN + "Crate Item Creation Menu");

        this.plugin = plugin;
        this.crate = crate;
        this.crateItem = item;

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

        //Min Amount
        this.setItem(18, new GUIItem(18, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+10", ChatColor.GRAY + "Minimum Amount"));
        this.setItem(19, new GUIItem(19, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+5", ChatColor.GRAY + "Minimum Amount"));
        this.setItem(20, new GUIItem(20, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+2", ChatColor.GRAY + "Minimum Amount"));
        this.setItem(21, new GUIItem(21, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "+1", ChatColor.GRAY + "Minimum Amount"));

        this.setItem(23, new GUIItem(23, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-1", ChatColor.GRAY + "Minimum Amount"));
        this.setItem(24, new GUIItem(24, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-2", ChatColor.GRAY + "Minimum Amount"));
        this.setItem(25, new GUIItem(25, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-5", ChatColor.GRAY + "Minimum Amount"));
        this.setItem(26, new GUIItem(26, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.RED + "-10", ChatColor.GRAY + "Minimum Amount"));

        //Max Amount
        this.setItem(27, new GUIItem(27, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+10", ChatColor.GRAY + "Maximum Amount"));
        this.setItem(28, new GUIItem(28, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+5", ChatColor.GRAY + "Maximum Amount"));
        this.setItem(29, new GUIItem(29, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+2", ChatColor.GRAY + "Maximum Amount"));
        this.setItem(30, new GUIItem(30, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "+1", ChatColor.GRAY + "Maximum Amount"));

        this.setItem(32, new GUIItem(32, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-1", ChatColor.GRAY + "Maximum Amount"));
        this.setItem(33, new GUIItem(33, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-2", ChatColor.GRAY + "Maximum Amount"));;
        this.setItem(34, new GUIItem(34, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-5", ChatColor.GRAY + "Maximum Amount"));
        this.setItem(35, new GUIItem(35, Material.PINK_STAINED_GLASS_PANE, ChatColor.RED + "-10", ChatColor.GRAY + "Maximum Amount"));
    }

    private void fillInformation()
    {
        this.setItem(4, new GUIItem(4, Material.ITEM_FRAME, ChatColor.GREEN + "Minimum Amount", ChatColor.GRAY + "" + crateItem.getMinAmount(), ChatColor.GREEN + "Maximum Amount", ChatColor.GRAY + "" + crateItem.getMaxAmount()));
    }

    private int getAmountFromItem(GUIItem item)
    {
        return Integer.valueOf(item.getStrippedName().replace("+", "").replace("-", ""));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if(item.getType() == Material.LIME_STAINED_GLASS_PANE)
        {
            crateItem.setMinAmount(crateItem.getMinAmount() + getAmountFromItem(e.getItem()));
            fillInformation();
        }

        if(item.getType() == Material.ORANGE_STAINED_GLASS_PANE)
        {
            if(crateItem.getMinAmount() - getAmountFromItem(e.getItem()) < 0) return;
            crateItem.setMinAmount(crateItem.getMinAmount() - getAmountFromItem(e.getItem()));
            fillInformation();
        }

        if(item.getType() == Material.GREEN_STAINED_GLASS_PANE)
        {
            crateItem.setMaxAmount(crateItem.getMaxAmount() + getAmountFromItem(e.getItem()));
            fillInformation();
        }

        if(item.getType() == Material.PINK_STAINED_GLASS_PANE)
        {
            if(crateItem.getMaxAmount() - getAmountFromItem(e.getItem()) < 0) return;
            crateItem.setMaxAmount(crateItem.getMaxAmount() - getAmountFromItem(e.getItem()));
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
