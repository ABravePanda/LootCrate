package lootcrate.gui.frame.crate;

import lootcrate.LootCrate;
import lootcrate.gui.CancelPolicy;
import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.gui.frame.PageableGuiFrame;
import lootcrate.managers.CacheManager;
import lootcrate.managers.CrateManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Arrays;
import java.util.List;

public class CrateRewardFrame extends PageableGuiFrame<CrateItem> {

    private final Crate crate;

    public CrateRewardFrame(LootCrate plugin, Player viewer, Crate crate, GuiFrame parentFrame) {
        super(plugin, 45, "§8» Edit Crate Rewards: §b" + crate.getName(), viewer, 21);
        this.crate = crate;
        this.setBackFrame(parentFrame);
    }

    @Override
    protected List<CrateItem> getElements() {
        return crate.getItems();
    }

    @Override
    protected GUIItem buildItem(CrateItem reward, int slot) {
        // Build off the existing item while preserving all metadata
        ItemBuilder builder = new ItemBuilder(reward.getItem(), plugin);

        // Append custom lore details without overriding existing ones
        builder.addLore(
                "",
                "§e§uData",
                "§7Chance: §f" + reward.getChance() + "%",
                "§7Amount: §f" + reward.getMinAmount() + " - " + reward.getMaxAmount()
        );

        if (!reward.getCommands().isEmpty()) {
            builder.addLore("§7Commands:");
            reward.getCommands().forEach(cmd -> builder.addLore(" §8- §f" + cmd));
        }

        builder.addLore(
                "",
                "§eLeft-click: Edit",
                "§cRight-click: Remove"
        );

        return GUIItem.builder()
                .slot(slot)
                .itemStack(builder.build())
                .cancelPolicy(CancelPolicy.ALWAYS)
                .onClick(ClickType.LEFT, ctx -> {
                    ctx.getPlayer().sendMessage("§eEditing reward items is not implemented yet.");
                    // TODO: Open reward editor
                })
                .onClick(ClickType.RIGHT, ctx -> {
                    crate.getItems().remove(reward);
                    ctx.getPlugin().getManager(CacheManager.class).update(crate);
                    ctx.getPlayer().sendMessage("§cRemoved reward item.");
                    render(); // Refresh display
                })
                .build();
    }


    @Override
    public void render() {
        super.render();

        // Title item
        setItem(GUIItem.builder()
                .slot(4)
                .itemStack(new ItemBuilder(Material.CHEST, plugin)
                        .name("§bCrate Reward Manager")
                        .lore(
                                "§7Manage rewards for:",
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
