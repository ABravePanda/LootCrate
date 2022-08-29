package lootcrate.gui.frames.creation.view;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.creation.items.CrateItemCreationFrame;
import lootcrate.gui.frames.menu.CrateFrame;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.Display;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateViewEditMainMenuFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private Display display;

    public CrateViewEditMainMenuFrame(LootCrate plugin, Player p, Crate crate, Display display) {
        super(plugin, p, ChatColor.GREEN + "Crate View Menu");

        this.plugin = plugin;
        this.crate = crate;
        this.display = display;

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
        this.setItem(20, new GUIItem(20, Material.CRAFTING_TABLE, ChatColor.GREEN + "Edit Display"));
        this.setItem(24, new GUIItem(24, Material.ITEM_FRAME, ChatColor.RED + "Edit Size"));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if(item.getType() == Material.ITEM_FRAME)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateViewSizeFrame(plugin, p, crate, display));
        }

        if(item.getType() == Material.CRAFTING_TABLE)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateViewEditFrame(plugin, p, crate, display));
        }

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateFrame(plugin, player, crate));
        return;
    }
}
