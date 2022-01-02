package lootcrate.gui.frames.animations;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.AnimatedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrateCSGOAnimationFrame extends AnimatedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private final long rewardSpeed = 3;
    private final int duration = 6;
    private int taskID;

    public CrateCSGOAnimationFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;

        generateFrame();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE, true);
        initLineup();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    /**
     * Displays animations for open sequence
     */
    public void showAnimation() {
        final int rewardID = animateReward();

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int timeLeft = duration;

            @Override
            public void run() {
                if (timeLeft == 0) {
                    Bukkit.getScheduler().cancelTask(rewardID);
                    fillBackground(Material.LIME_STAINED_GLASS_PANE, false);
                    giveRewards(getContents()[22].getCrateItem());
                }
                if (timeLeft == -3) {
                    closeFrame(player, getAnimatedFrame());
                    Bukkit.getScheduler().cancelTask(taskID);
                }
                timeLeft--;
            }
        }, 0L, 20L);
    }

    // animates rewards
    private int animateReward() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                setItem(20, getContents()[21]);
                setItem(21, getContents()[22]);
                setItem(22, getContents()[23]);
                setItem(23, getContents()[24]);
                setItem(24, new GUIItem(24, plugin.getCrateManager().getRandomItem(crate)));
                // getContents()[]
            }
        }, 0L, this.rewardSpeed);
    }

    private void initLineup() {
        setItem(20, new GUIItem(22, plugin.getCrateManager().getRandomItem(crate)));
        setItem(21, new GUIItem(22, plugin.getCrateManager().getRandomItem(crate)));
        setItem(22, new GUIItem(22, plugin.getCrateManager().getRandomItem(crate)));
        setItem(23, new GUIItem(22, plugin.getCrateManager().getRandomItem(crate)));
        setItem(24, new GUIItem(22, plugin.getCrateManager().getRandomItem(crate)));
    }

    private void giveRewards(CrateItem crateItem) {
        plugin.getCrateManager().giveReward(crateItem, getViewer());
    }

    public void fillBackground(Material m, boolean showRewardsPointer) {
        int index = 0;
        while (index < getInventory().getSize()) {
            if (index != 22)
                this.setItem(index, new GUIItem(index, m));
            index++;
        }
        if (showRewardsPointer) {
            this.setItem(13, new GUIItem(13, Material.REDSTONE_TORCH, "&cReward"));
            this.setItem(31, new GUIItem(31, Material.REDSTONE_TORCH, "&cReward"));
        }
    }

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;
        e.setCancelled(true);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
