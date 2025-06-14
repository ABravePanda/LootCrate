package lootcrate.gui.frame.crate;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.AbstractGuiFrame;
import lootcrate.gui.frame.ConfirmationFrame;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.managers.CacheManager;
import lootcrate.managers.CrateManager;
import lootcrate.managers.GuiManager;
import lootcrate.objects.Crate;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class CrateFrame extends AbstractGuiFrame {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateFrame(LootCrate plugin, Player viewer, Crate crate) {
        super(plugin, 45, "§8» Edit Crate: §b" + crate.getName(), viewer);
        this.plugin = plugin;
        this.crate = crate;
    }

    @Override
    public void render() {
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, plugin).name(" ").build();
        ItemStack border = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, plugin).name(" ").build();

        for (int i = 0; i < getSize(); i++) {
            setItem(GUIItem.of(filler, i));
        }

        // Info summary (top center)
        setItem(GUIItem.builder()
                .slot(4)
                .itemStack(new ItemBuilder(Material.PAPER, plugin)
                        .name("§eCrate Info")
                        .lore(
                                "§7Name: §f" + crate.getName(),
                                "§7ID: §f" + crate.getId(),
                                "§7Items: §f" + crate.getItems().size(),
                                "§7Key: §f" + (crate.getKey() != null ? crate.getKey() : "§cNot Set")
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .build());

        // ========== Action Buttons ==========

        // Crate Rewards
        setItem(GUIItem.builder()
                .slot(19)
                .itemStack(new ItemBuilder(Material.CHEST, plugin)
                        .name("§aCrate Rewards")
                        .lore("§7Edit or view all", "§7items in this crate.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getGuiManager().open(ctx.getPlayer(), new CrateRewardFrame(plugin, ctx.getPlayer(), crate, this));
                })
                .build());

        // Crate Locations
        setItem(GUIItem.builder()
                .slot(21)
                .itemStack(new ItemBuilder(Material.ENDER_EYE, plugin)
                        .name("§aCrate Locations")
                        .lore("§7Manage crate spawn locations", "§7in the world.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getGuiManager().open(ctx.getPlayer(), new CrateLocationFrame(plugin, ctx.getPlayer(), crate, this));
                })
                .build());

        // Crate Properties (name, id, etc.)
        setItem(GUIItem.builder()
                .slot(23)
                .itemStack(new ItemBuilder(Material.NAME_TAG, plugin)
                        .name("§aCrate Properties")
                        .lore("§7Edit the crate's name,", "§7ID and description.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().sendMessage("§aOpening property editor...");
                    // TODO
                })
                .build());

        // Crate Options (effects, etc.)
        setItem(GUIItem.builder()
                .slot(25)
                .itemStack(new ItemBuilder(Material.COMPARATOR, plugin)
                        .name("§aCrate Options")
                        .lore("§7Configure animations,", "§7effects, and behavior.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().sendMessage("§aOpening options...");
                    // TODO
                })
                .build());

        // Preview Roll
        setItem(GUIItem.builder()
                .slot(31)
                .itemStack(new ItemBuilder(Material.NETHER_STAR, plugin)
                        .name("§bPreview Roll")
                        .lore("§7Simulate the crate roll", "§7without using a key.")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().sendMessage("§aStarting preview roll...");
                    // TODO
                })
                .build());

        // Delete Crate
        setItem(GUIItem.builder()
                .slot(40)
                .itemStack(new ItemBuilder(Material.BARRIER, plugin)
                        .name("§c§lDelete Crate")
                        .lore("§7Permanently delete", "§7this crate. §c(Warning!)")
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    Player player = ctx.getPlayer();
                    GuiFrame frame = new ConfirmationFrame(plugin, player, this,"Delete Crate", "§c§lDelete This Crate?",
                            new String[]{"§7You are about to permanently", "§7delete the crate §f" + crate.getName() + "§7.", "", "§cThis cannot be undone!"}, () -> {
                                plugin.getManager(CacheManager.class).remove(crate);
                                player.sendMessage("§cCrate '" + crate.getName() + "' deleted.");
                            }, () -> {
                                player.sendMessage("§7Cancelled crate deletion.");
                            });
                    getPlugin().getManager(GuiManager.class).open(player, frame);

                })
                .build());


        // Decorative corners
        for (int slot : new int[]{0, 8, 36, 44, 27, 35}) {
            setItem(GUIItem.of(border, slot));
        }
    }

    @Override
    public boolean preventsClose() {
        return false;
    }

    @Override
    public void tick() {
        // Optional: animations or updates
    }


    public Crate getCrate() {
        return crate;
    }

    public LootCrate getPlugin() {
        return plugin;
    }
}

