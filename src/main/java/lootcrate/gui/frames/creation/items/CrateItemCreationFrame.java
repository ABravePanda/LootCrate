package lootcrate.gui.frames.creation.items;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateItemCreationFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateItemCreationFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, ChatColor.GREEN + "Crate Item Creation Menu");

        this.plugin = plugin;
        this.crate = crate;

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillItems();
        fillItemSpot();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }



    private void fillItems()
    {
        this.setItem(10, new GUIItem(10, Material.BEACON, ChatColor.GREEN + "Chance", ChatColor.GRAY + "50%"));
        this.setItem(28, new GUIItem(28, Material.GLASS, ChatColor.GREEN + "Display Only", ChatColor.GRAY + "False"));
        this.setItem(16, new GUIItem(16, Material.COMMAND_BLOCK, ChatColor.GREEN + "Commands", ChatColor.GRAY + "0 Commands"));
        this.setItem(25, new GUIItem(25, Material.BUCKET, ChatColor.GREEN + "Minimum Amount", ChatColor.GRAY + "1"));
        this.setItem(34, new GUIItem(34, Material.WATER_BUCKET, ChatColor.GREEN + "Maximum Amount", ChatColor.GRAY + "5"));
    }

    private void fillItemSpot()
    {
        this.setItem(12, new GUIItem(12, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
        this.setItem(13, new GUIItem(13, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
        this.setItem(14, new GUIItem(14, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
        this.setItem(21, new GUIItem(21, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
        this.setItem(22, new GUIItem(14, Material.AIR));
        this.setItem(23, new GUIItem(23, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
        this.setItem(30, new GUIItem(30, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
        this.setItem(31, new GUIItem(31, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
        this.setItem(32, new GUIItem(32, Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + "Place item here"));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if(item.getType() == Material.CHEST)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemFrame(plugin, p, crate));
        }

        if(item.getType() == Material.CRAFTING_TABLE)
        {

        }

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
