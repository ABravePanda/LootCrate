package lootcrate.gui.frames.menu.option;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class CrateOptionKnockBackFrame extends BasicFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateOptionKnockBackFrame(LootCrate plugin, Player p, Crate crate) {
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

    public void fillBackground(Material m) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, m));
        }
    }

    public void fillOptions() {
        DecimalFormat df = new DecimalFormat("###.#");
        double crateKnockback = (double) crate.getOption(CrateOptionType.KNOCK_BACK).getValue();
        this.setItem(13, new GUIItem(13, Material.STICK, ChatColor.GOLD + "Knockback Level",
                ChatColor.GRAY + "" + df.format(crateKnockback)));
        this.setItem(30, new GUIItem(30, Material.IRON_NUGGET, ChatColor.RED + "- 0.1"));
        this.setItem(32, new GUIItem(32, Material.IRON_INGOT, ChatColor.GREEN + "+ 0.1"));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        switch (item.getType()) {
            case IRON_NUGGET:
                crate.setOption(new CrateOption(CrateOptionType.KNOCK_BACK,
                        (Double) crate.getOption(CrateOptionType.KNOCK_BACK).getValue() - 0.100));
                plugin.getCacheManager().update(crate);
                break;
            case IRON_INGOT:
                crate.setOption(new CrateOption(CrateOptionType.KNOCK_BACK,
                        (Double) crate.getOption(CrateOptionType.KNOCK_BACK).getValue() + 0.100));
                plugin.getCacheManager().update(crate);
                break;
            default:
                return;
        }

        this.close();
        new CrateOptionKnockBackFrame(plugin, p, crate).open();

    }

}
