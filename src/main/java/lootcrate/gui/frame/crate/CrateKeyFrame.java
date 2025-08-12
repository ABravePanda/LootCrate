package lootcrate.gui.frame.crate;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.AbstractGuiFrame;
import lootcrate.gui.frame.ConfirmationFrame;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.gui.frame.SetItemFrame;
import lootcrate.managers.CacheManager;
import lootcrate.managers.GuiManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateKey;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CrateKeyFrame extends AbstractGuiFrame {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateKeyFrame(LootCrate plugin, Player viewer, Crate crate, GuiFrame parentFrame) {
        super(plugin, 36, "§8» Edit Crate Key: §b" + crate.getName(), viewer);
        this.plugin = plugin;
        this.crate = crate;
        this.setBackFrame(parentFrame);
    }


    @Override
    public void render() {
        boolean hasKey = crate.getKey() != null && crate.getKey().getItem() != null;

        // Background filler
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, plugin).name(" ").build();
        for (int i = 0; i < getSize(); i++) {
            setItem(GUIItem.of(filler, i));
        }

        // Border (top and bottom rows + sides)
        ItemStack border = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, plugin).name(" ").build();
        for (int slot = 0; slot < getSize(); slot++) {
            int row = slot / 9;
            int col = slot % 9;
            if (row == 0 || row == (size / 9 - 1) || col == 0 || col == 8) {
                setItem(GUIItem.of(border, slot));
            }
        }

        // Crate Info Panel
        setItem(GUIItem.builder()
                .slot(4)
                .itemStack(new ItemBuilder(Material.BOOK, plugin)
                        .name("§6§lCrate Information")
                        .lore(
                                "§7Name: §f" + crate.getName(),
                                "§7ID: §f" + crate.getId(),
                                "§7Items: §f" + crate.getItems().size(),
                                "",
                                "§7Key: " + (hasKey
                                        ? "§f" + crate.getKey().getItem().getType()
                                        : "§cNot Set")
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .build());

        // Set Key Button
        setItem(GUIItem.builder()
                .slot(12)
                .itemStack(new ItemBuilder(Material.TRIPWIRE_HOOK, plugin)
                        .name(hasKey ? "§eChange Crate Key" : "§aSet Crate Key")
                        .lore(
                                hasKey
                                        ? new String[]{
                                        "§7Click to replace the current crate key.",
                                        "",
                                        "§8Current: §f" + crate.getKey().getItem().getType()
                                }
                                        : new String[]{
                                        "§7Click to set a new crate key.",
                                        "",
                                        "§cNo key is currently set."
                                }
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    SetItemFrame frame = new SetItemFrame(
                            plugin, ctx.getPlayer(), this,
                            "Set Crate Key",
                            "§ePlace New Key",
                            new String[]{"§7Put the new crate key item here."},
                            item -> {
                                CrateKey crateKey = new CrateKey(item, false);
                                crate.setKey(crateKey);
                                plugin.getManager(CacheManager.class).update(crate);
                                ctx.getPlayer().sendMessage("§aCrate key set to: §f" + item.getType());
                            },
                            () -> ctx.getPlayer().sendMessage("§7Cancelled setting crate key.")
                    );
                    plugin.getManager(GuiManager.class).open(ctx.getPlayer(), frame);
                })
                .build());

        // Delete Key Button
        setItem(GUIItem.builder()
                .slot(14)
                .itemStack(new ItemBuilder(Material.BARRIER, plugin)
                        .name("§cDelete Crate Key")
                        .lore(
                                hasKey
                                        ? new String[]{"§7Click to remove the current key.", "", "§cThis action cannot be undone!"}
                                        : new String[]{"§7No crate key is set."}
                        )
                        .build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    Player player = ctx.getPlayer();
                    if (!hasKey) {
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

        // Back Button
        if (getBackframe() != null) {
            setItem(GUIItem.builder()
                    .slot(getSize() - 9) // bottom-left
                    .itemStack(new ItemBuilder(Material.ARROW, plugin)
                            .name("§eBack")
                            .lore("§7Return to previous menu.")
                            .build())
                    .cancelPolicy(CancelPolicy.ALWAYS)
                    .onClick(ClickType.LEFT, ctx -> ctx.getGuiManager().open(ctx.getPlayer(), getBackframe()))
                    .build());
        }
    }

    @Override
    public boolean preventsClose() {
        return false;
    }

    @Override
    public void tick() {
        // Optional animations can be added here
    }

    public Crate getCrate() {
        return crate;
    }

    public LootCrate getPlugin() {
        return plugin;
    }
}
