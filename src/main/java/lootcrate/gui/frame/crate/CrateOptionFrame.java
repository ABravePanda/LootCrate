package lootcrate.gui.frame.crate;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.gui.frame.PageableGuiFrame;
import lootcrate.managers.CrateManager;
import lootcrate.managers.LocationManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class CrateOptionFrame extends PageableGuiFrame<CrateOptionFrame.CrateOptionDisplay> {

    private final Crate crate;
    private final CrateManager crateManager;

    public CrateOptionFrame(LootCrate plugin, Player viewer, Crate crate, GuiFrame parentFrame) {
        super(plugin, 45, "§8» Edit Crate Locations: §b" + crate.getName(), viewer, 21);
        this.crate = crate;
        this.crateManager = plugin.getManager(CrateManager.class);
        this.setBackFrame(parentFrame); // optional back button
    }

    @Override
    protected List<CrateOptionDisplay> getElements() {
        return crate.getOptions().entrySet().stream()
                .map(entry -> new CrateOptionDisplay(new CrateOption(entry.getKey(), entry.getValue()), "Test"))
                .toList();
    }


    @Override
    protected GUIItem buildItem(CrateOptionDisplay element, int slot) {
        int index = cachedElements.indexOf(element) + 1;
        CrateOption crateOption = element.crateOption;

        return GUIItem.builder()
                .slot(slot)
                .itemStack(new ItemBuilder(
                        Material.ITEM_FRAME, plugin)
                        .name("§e" + crateOption.getKey().getKey())
                        .lore(
                                "§7" + crateOption.getKey().getDescription(),
                                "",
                                "§7Current Value:",
                                "§b" + crateOption.getValue().toString()
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .build();
    }

    @Override
    public void render() {
        super.render();

        // Title item in center
        setItem(GUIItem.builder()
                .slot(4)
                .itemStack(new ItemBuilder(Material.BOOK, plugin)
                        .name("§bCrate Option Manager")
                        .lore(
                                "§7Manage options for:",
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

    public class CrateOptionDisplay {
        public CrateOption crateOption;
        public String description;

        public CrateOptionDisplay(CrateOption crateOption, String description) {
            this.crateOption = crateOption;
            this.description = description;
        }
    }
}
