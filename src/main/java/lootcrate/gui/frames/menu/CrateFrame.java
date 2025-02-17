package lootcrate.gui.frames.menu;

import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.creation.items.CrateItemFrame;
import lootcrate.gui.frames.menu.option.CrateOptionMainMenuFrame;
import lootcrate.gui.frames.types.BaseFrame;
import lootcrate.gui.frames.types.Frame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CustomizationManager;
import lootcrate.objects.Crate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateFrame extends BaseFrame implements Listener {

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
        this.setItem(19, new GUIItem(19, Material.BRICKS,
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_ITEMS_NAME),
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_ITEMS_LORE)));
        this.setItem(12, new GUIItem(12, Material.TRIPWIRE_HOOK,
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_KEY_NAME),
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_KEY_LORE)));
        this.setItem(14, new GUIItem(14, Material.ENDER_PEARL,
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_LOCATIONS_NAME),
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_LOCATIONS_LORE)));
        this.setItem(25, new GUIItem(25, Material.ANVIL,
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_OPTIONS_NAME),
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_VIEW_OPTIONS_LORE)));
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

    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateListFrame(plugin, player));
    }
}
