package lootcrate.gui.frame.crate;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.AbstractGuiFrame;
import lootcrate.gui.frame.ConfirmationFrame;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.managers.CacheManager;
import lootcrate.managers.GuiManager;
import lootcrate.objects.Crate;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CrateKeyFrame extends AbstractGuiFrame {

    private final LootCrate plugin;
    private final Crate crate;
    private GuiFrame backFrame;

    public CrateKeyFrame(LootCrate plugin, Player viewer, Crate crate, GuiFrame parentFrame) {
        super(plugin, 36, "§8» Edit Crate Key: §b" + crate.getName(), viewer);
        this.plugin = plugin;
        this.crate = crate;
        this.setBackFrame(parentFrame);
    }

    public void setBackFrame(GuiFrame backFrame) {
        this.backFrame = backFrame;
    }

    @Override
    public void render() {
        // Fill background with glass
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, plugin).name(" ").build();
        for (int i = 0; i < getSize(); i++) {
            setItem(GUIItem.of(filler, i));
        }

        // Crate info panel
        setItem(GUIItem.builder()
                .slot(4)
                .itemStack(new ItemBuilder(Material.PAPER, plugin)
                        .name("§eCrate Info")
                        .lore(
                                "§7Name: §f" + crate.getName(),
                                "§7ID: §f" + crate.getId(),
                                "§7Items: §f" + crate.getItems().size(),
                                "§7Key: §f" + (crate.getKey() != null
                                        ? crate.getKey().getItem().getType()
                                        : "§cNot Set")
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .build());

        // --- Set Key button ---
        setItem(GUIItem.builder()
                .slot(12)
                .itemStack(new ItemBuilder(
                        crate.getKey() != null ? Material.LIME_CONCRETE : Material.LIME_STAINED_GLASS_PANE,
                        plugin)
                        .name("§aSet Crate Key")
                        .lore(
                                "§7Click to set or replace the crate key.",
                                "",
                                crate.getKey() != null
                                        ? "§8Current: §f" + crate.getKey().getItem().getType()
                                        : "§cNo key set"
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().sendMessage("§e[Coming Soon] Set crate key feature.");
                    // TODO: Implement set key logic (e.g., take held item or open key selection GUI)
                })
                .build());

        // --- Delete Key button ---
        setItem(GUIItem.builder()
                .slot(14)
                .itemStack(new ItemBuilder(
                        crate.getKey() != null ? Material.BARRIER : Material.BARRIER,
                        plugin)
                        .name("§cDelete Crate Key")
                        .lore(
                                crate.getKey() != null
                                        ? new String[]{"§7Click to remove the current key.", "", "§cThis action cannot be undone!"}
                                        : new String[]{"§7No crate key is set."}
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    Player player = ctx.getPlayer();
                    if (crate.getKey() == null) {
                        player.sendMessage("§cThis crate has no key set.");
                        return;
                    }

                    GuiFrame confirm = new ConfirmationFrame(
                            plugin, player, this,
                            "Delete Key", "§c§lDelete This Key?",
                            new String[]{
                                    "§7You are about to permanently",
                                    "§7delete this key.",
                                    "",
                                    "§cThis cannot be undone!"
                            },
                            () -> {
                                crate.setKey(null);
                                plugin.getManager(CacheManager.class).update(crate);
                                player.sendMessage("§cKey deleted.");
                                plugin.getManager(GuiManager.class).open(player, this);
                            },
                            () -> player.sendMessage("§7Cancelled key deletion.")
                    );

                    plugin.getManager(GuiManager.class).open(player, confirm);
                })
                .build());

        // Back button
        if (backFrame != null) {
            setItem(GUIItem.builder()
                    .slot(getSize() - 9) // bottom-left
                    .itemStack(new ItemBuilder(Material.PAPER, plugin)
                            .name("§eBack")
                            .lore("§7Return to previous menu.")
                            .build())
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .onClick(ClickType.LEFT, ctx -> ctx.getGuiManager().open(ctx.getPlayer(), backFrame))
                    .build());
        }
    }

    @Override
    public boolean preventsClose() {
        return false;
    }

    @Override
    public void tick() {
        // No animations needed here
    }

    public Crate getCrate() {
        return crate;
    }

    public LootCrate getPlugin() {
        return plugin;
    }
}
