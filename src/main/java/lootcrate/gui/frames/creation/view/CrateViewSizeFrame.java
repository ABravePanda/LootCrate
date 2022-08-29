package lootcrate.gui.frames.creation.view;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.creation.items.CrateItemCreationFrame;
import lootcrate.gui.frames.creation.items.CrateItemFrame;
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

public class CrateViewSizeFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private Display display;
    private GUIItem nine = new GUIItem(20, Material.CHEST, ChatColor.GREEN + "9x1 | 9 Slots");
    private GUIItem eighteen = new GUIItem(21, Material.CHEST, ChatColor.GREEN + "9x2 | 18 Slots");
    private GUIItem twentyseven = new GUIItem(22, Material.CHEST, ChatColor.GREEN + "9x3 | 27 Slots");
    private GUIItem thirtysix = new GUIItem(23, Material.CHEST, ChatColor.GREEN + "9x4 | 36 Slots");
    private GUIItem fourtyfive = new GUIItem(24, Material.CHEST, ChatColor.GREEN + "9x5 | 45 Slots");
    private GUIItem fiftyfour = new GUIItem(31, Material.CHEST, ChatColor.GREEN + "9x6 | 54 Slots");

    public CrateViewSizeFrame(LootCrate plugin, Player p, Crate crate, Display display) {
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
        this.setItem(20, nine);
        this.setItem(21, eighteen);
        this.setItem(22, twentyseven);
        this.setItem(23, thirtysix);
        this.setItem(24, fourtyfive);
        this.setItem(31, fiftyfour);

    }

    private void disableGlowing()
    {
        nine.setGlowing(false);
        eighteen.setGlowing(false);
        twentyseven.setGlowing(false);
        thirtysix.setGlowing(false);
        fourtyfive.setGlowing(false);
        fiftyfour.setGlowing(false);
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();


        if(e.getItem().equals(nine)) {
            this.display.setSize(9);
            disableGlowing();
            nine.setGlowing(true);
            this.setItem(nine.getSlot(), nine);
        }
        else if(e.getItem().equals(eighteen)) {
            this.display.setSize(18);
            disableGlowing();
            eighteen.setGlowing(true);
            this.setItem(eighteen.getSlot(), eighteen);
        }
        else if(e.getItem().equals(twentyseven)) {
            this.display.setSize(27);
            disableGlowing();
            twentyseven.setGlowing(true);
            this.setItem(twentyseven.getSlot(), twentyseven);
        }
        else if(e.getItem().equals(thirtysix)) {
            this.display.setSize(36);
            disableGlowing();
            thirtysix.setGlowing(true);
            this.setItem(thirtysix.getSlot(), thirtysix);
        }
        else if(e.getItem().equals(fourtyfive)) {
            this.display.setSize(45);
            disableGlowing();
            fourtyfive.setGlowing(true);
            this.setItem(fourtyfive.getSlot(), fourtyfive);
        }
        else if(e.getItem().equals(fiftyfour)) {
            this.display.setSize(54);
            disableGlowing();
            fiftyfour.setGlowing(true);
            this.setItem(fiftyfour.getSlot(), fiftyfour);
        } else return;

        this.crate.setDisplay(display);
        this.plugin.getCacheManager().update(crate);

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
