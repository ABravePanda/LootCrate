package lootcrate.gui.frames.creation;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.menu.CrateFrame;
import lootcrate.gui.frames.menu.CrateMainMenuFrame;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CrateCreateFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final List<Crate> crates;

    public CrateCreateFrame(LootCrate plugin, Player p) {
        super(plugin, p, ChatColor.GREEN + "Crate Main Menu");

        this.plugin = plugin;
        crates = plugin.getCacheManager().getCache();

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillItems();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }



    private void fillItems()
    {
        this.setItem(20, new GUIItem(20, Material.CRAFTING_TABLE, ChatColor.GREEN + "Create New Crate"));
        this.setItem(24, new GUIItem(24, Material.CHEST, ChatColor.GREEN + "View All Crates"));
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
            this.openFrame(p, new CrateMainMenuFrame(plugin, p));
        }

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
