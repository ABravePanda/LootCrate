package lootcrate.gui.frames.menu;

import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.enums.CustomizationOption;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CacheManager;
import lootcrate.managers.ChatManager;
import lootcrate.managers.CustomizationManager;
import lootcrate.objects.Crate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrateMainMenuFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final List<Crate> crates;

    public CrateMainMenuFrame(LootCrate plugin, Player p) {
        super(plugin, p, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATES_MAIN_MENU_TITLE));

        this.plugin = plugin;
        crates = plugin.getManager(CacheManager.class).getCache();

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
        this.setItem(20, new GUIItem(20, Material.CRAFTING_TABLE, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.MAIN_CREATE_NEW_CRATE)));
        this.setItem(24, new GUIItem(24, Material.CHEST, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.MAIN_VIEW_ALL_CRATES)));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
        {
            e.setCancelled(true);
            return;
        }

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if(item.getType() == Material.CHEST)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateListFrame(plugin, p));
        }

        if(item.getType() == Material.CRAFTING_TABLE)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            ChatState state = ChatState.CREATE_CRATE_NAME;
            plugin.getManager(ChatManager.class).addPlayer(p, state);
            plugin.getManager(ChatManager.class).sendNotification(p);
        }
        e.setCancelled(true);

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
