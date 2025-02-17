package lootcrate.gui.frames.menu.option;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.CustomizationOption;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BaseFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CacheManager;
import lootcrate.managers.CustomizationManager;
import lootcrate.managers.HologramManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrateOptionHologramEnabledFrame extends BaseFrame implements Listener {

    private final LootCrate plugin;
    private final HologramManager hologramManager;
    private final Crate crate;
    private final GUIItem enabled;
    private final GUIItem disabled;
    private List<Sound> soundList;

    public CrateOptionHologramEnabledFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        enabled = new GUIItem(13, Material.NAME_TAG,
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_HOLOGRAM_VISIBLE_NAME),
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_HOLOGRAM_ENABLED));
        disabled = new GUIItem(13, Material.NAME_TAG,
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_HOLOGRAM_VISIBLE_NAME),
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_HOLOGRAM_DISABLED));
        this.enabled.setGlowing(true);
        this.hologramManager = plugin.getHoloManager();

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

    public void fillBackground(Material m) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, m));
        }
    }

    public void fillOptions() {
        if(plugin.getHoloManager() == null)
        {
            fillBackground(Material.GRAY_STAINED_GLASS_PANE);
            this.setItem(13, new GUIItem(13, Material.RED_BANNER, ChatColor.RED + "Holograms Disabled",
                    ChatColor.DARK_GRAY + "No Hologram Plugin Found"));
            return;
        }

        if(isHologramEnabled())
            this.setItem(13, enabled);
        else
            this.setItem(13, disabled);

        this.setItem(20, new GUIItem(20, Material.SLIME_BALL, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_HOLOGRAM_ENABLE)));
        this.setItem(24, new GUIItem(24, Material.FIRE_CHARGE, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_HOLOGRAM_DISABLE)));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        switch (item.getType()) {
            case SLIME_BALL:
                crate.setOption(new CrateOption(CrateOptionType.HOLOGRAM_ENABLED, true));
                plugin.getManager(CacheManager.class).update(crate);
                plugin.getHoloManager().reload();
                fillOptions();
                break;
            case FIRE_CHARGE:
                crate.setOption(new CrateOption(CrateOptionType.HOLOGRAM_ENABLED, false));
                plugin.getManager(CacheManager.class).update(crate);
                plugin.getHoloManager().reload();
                fillOptions();
                break;
            default:
                break;
        }

        e.setCancelled(true);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateOptionMainMenuFrame(plugin, player, crate));
        return;
    }

    private boolean isHologramEnabled()
    {
        return (boolean) crate.getOption(CrateOptionType.HOLOGRAM_ENABLED).getValue();
    }
}