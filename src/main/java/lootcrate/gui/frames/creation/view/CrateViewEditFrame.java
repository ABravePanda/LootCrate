package lootcrate.gui.frames.creation.view;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.menu.CrateFrame;
import lootcrate.gui.frames.types.CustomSizeFrame;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.frames.types.InputAllowed;
import lootcrate.gui.frames.types.ShiftClickAllowed;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.Display;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateViewEditFrame extends CustomSizeFrame implements Listener, InputAllowed, ShiftClickAllowed {

    private final LootCrate plugin;
    private final Crate crate;
    private Display display;
    public CrateViewEditFrame(LootCrate plugin, Player p, Crate crate, Display display) {
        super(plugin, p, ChatColor.GREEN + "Crate View Menu", display.getSize());

        this.plugin = plugin;
        this.crate = crate;
        this.display = display;

        generateFrame();
        //generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        //fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillItems();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }



    private void fillItems()
    {

    }


    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();
        p.sendMessage("Input detected");
        e.setCancelled(false);

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
