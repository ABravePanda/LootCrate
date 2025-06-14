package lootcrate.gui.frame.crate;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.gui.frame.PageableGuiFrame;
import lootcrate.managers.LocationManager;
import lootcrate.objects.Crate;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class CrateLocationFrame extends PageableGuiFrame<Location> {

    private final Crate crate;
    private final LocationManager locationManager;

    public CrateLocationFrame(LootCrate plugin, Player viewer, Crate crate, GuiFrame parentFrame) {
        super(plugin, 45, "§8» Edit Crate Locations: §b" + crate.getName(), viewer, 21);
        this.crate = crate;
        this.locationManager = plugin.getManager(LocationManager.class);
        this.setBackFrame(parentFrame); // optional back button
    }

    @Override
    protected List<Location> getElements() {
        return locationManager.getCrateLocations(crate);
    }

    @Override
    protected GUIItem buildItem(Location loc, int slot) {
        int index = cachedElements.indexOf(loc) + 1;
        Material blockType = loc.getBlock().getType();

        return GUIItem.builder()
                .slot(slot)
                .itemStack(new ItemBuilder(
                        blockType != Material.AIR ? blockType : Material.BEACON, plugin)
                        .name("§bLocation #" + index)
                        .lore(
                                "§7World: §f" + loc.getWorld().getName(),
                                "§7X: §f" + loc.getBlockX(),
                                "§7Y: §f" + loc.getBlockY(),
                                "§7Z: §f" + loc.getBlockZ(),
                                "§7Block: §f" + blockType,
                                "",
                                "§eLeft-click: Teleport",
                                "§cRight-click: Remove"
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().teleport(loc);
                    ctx.getPlayer().sendMessage("§aTeleported to location #" + index + ".");
                })
                .onClick(ClickType.RIGHT, ctx -> {
                    locationManager.removeCrateLocation(loc);
                    ctx.getPlayer().sendMessage("§cRemoved location #" + index + ".");
                    render(); // triggers full re-render
                })
                .build();
    }

    @Override
    public void render() {
        super.render();

        // Title item in center
        setItem(GUIItem.builder()
                .slot(4)
                .itemStack(new ItemBuilder(Material.BOOK, plugin)
                        .name("§bCrate Location Manager")
                        .lore(
                                "§7Manage locations for:",
                                "§f" + crate.getName()
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .build());
    }

    @Override
    public boolean preventsClose() {
        return false;
    }
}
