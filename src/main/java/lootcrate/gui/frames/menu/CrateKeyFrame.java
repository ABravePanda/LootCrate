package lootcrate.gui.frames.menu;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateKey;
import lootcrate.utils.ObjUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateKeyFrame extends BasicFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateKeyFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;

        registerFrame();
        generateFrame();
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
        this.setItem(13, new GUIItem(13, crate.getKey()));
        this.setItem(29, new GUIItem(29, Material.LIME_DYE, ChatColor.GREEN + "Set Key"));
        this.setItem(31, new GUIItem(31, Material.AIR));
        this.setItem(33, new GUIItem(33, Material.RED_DYE, ChatColor.RED + "Cancel"));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        Player p = e.getPlayer();

        if (!e.sameFrame(this))
            return;

        if (e.getClickEvent().getSlot() == 31)
            return;

        if (e.getClickEvent().getSlot() == 13) {
            p.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
            return;
        }

        ItemStack item = e.getItem().getItemStack();

        switch (item.getType()) {
            case LIME_DYE:
                if (p.getOpenInventory().getTopInventory().getItem(31) == null)
                    return;
                crate.setKey(new CrateKey(this.getInventory().getItem(31), false));
                plugin.getCacheManager().update(crate);
                this.closeFrame(p, this);
                this.openFrame(p, new CrateKeyFrame(plugin, p, crate));
                break;
            case RED_DYE:
                if (p.getOpenInventory().getTopInventory().getItem(31) == null)
                    return;
                p.getInventory().addItem(this.getInventory().getItem(31));
                this.setItem(31, new GUIItem(31, Material.AIR));
                break;
            default:
                return;
        }
    }

}
