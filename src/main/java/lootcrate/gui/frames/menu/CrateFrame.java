package lootcrate.gui.frames.menu;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.menu.option.CrateOptionMainMenuFrame;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateFrame extends BasicFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName() + " : " + crate.getId());

        this.plugin = plugin;
        this.crate = crate;

        registerFrame();
        generateFrame();
        generateNavigation();
        registerItems();
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

    public void fillOptions() {
        this.setItem(10, new GUIItem(10, Material.BRICKS, ChatColor.RED + "Items",
                ChatColor.GRAY + "All the items in the crate."));
        this.setItem(13, new GUIItem(13, Material.TRIPWIRE_HOOK, ChatColor.RED + "Key",
                ChatColor.GRAY + "Used to unlock the crate."));
        this.setItem(16, new GUIItem(16, Material.ENDER_PEARL, ChatColor.RED + "Locations",
                ChatColor.GRAY + "All the places the crate is set."));
        this.setItem(31, new GUIItem(31, Material.ANVIL, ChatColor.RED + "Options",
                ChatColor.GRAY + "Edit crate name, hologram, knockback, etc..."));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        Frame frameToOpen = null;

        switch (item.getType()) {
            case BRICKS:
                frameToOpen = new CrateItemFrame(plugin, p, crate);
                break;
            case TRIPWIRE_HOOK:
                frameToOpen = new CrateKeyFrame(plugin, p, crate);
                break;
            case ENDER_PEARL:
                frameToOpen = new CrateLocationFrame(plugin, p, crate);
                break;
            case ANVIL:
                frameToOpen = new CrateOptionMainMenuFrame(plugin, p, crate);
                break;
            default:
                return;
        }

        this.closeFrame(p, this);
        this.openFrame(p, frameToOpen);
    }

    @Override
    public void nextPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateItemFrame(plugin, player, crate));
    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateMainMenuFrame(plugin, player));
    }
}
