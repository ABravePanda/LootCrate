package lootcrate.gui.frames.animations;

import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.AnimatedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CrateManager;
import lootcrate.managers.CustomizationManager;
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
    private long rewardSpeed = 3;
    private int duration = 6;
    private int taskID;
    private CustomizationManager customizationManager;

    public CrateCSGOAnimationFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.customizationManager = plugin.getManager(CustomizationManager.class);
        this.duration = (int) customizationManager.parseLong(CustomizationOption.CSGO_ANIMATION_DURATION);
        this.rewardSpeed = customizationManager.parseLong(CustomizationOption.CSGO_ANIMATION_SCROLL_SPEED);

        generateFrame();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(customizationManager.parseMaterial(CustomizationOption.CSGO_ANIMATION_BACKGROUND_MATERIAL), customizationManager.parseString(CustomizationOption.CSGO_ANIMATION_BACKGROUND_NAME), true);
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
                    fillBackground(customizationManager.parseMaterial(CustomizationOption.CSGO_ANIMATION_WINNER_BACKGROUND_MATERIAL), customizationManager.parseString(CustomizationOption.CSGO_ANIMATION_WINNER_BACKGROUND_NAME), false);
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
                setItem(24, new GUIItem(24, plugin.getManager(CrateManager.class).getRandomItem(crate)));
                // getContents()[]
            }
        }, 0L, this.rewardSpeed);
    }

    private void initLineup() {
        setItem(20, new GUIItem(22, plugin.getManager(CrateManager.class).getRandomItem(crate)));
        setItem(21, new GUIItem(22, plugin.getManager(CrateManager.class).getRandomItem(crate)));
        setItem(22, new GUIItem(22, plugin.getManager(CrateManager.class).getRandomItem(crate)));
        setItem(23, new GUIItem(22, plugin.getManager(CrateManager.class).getRandomItem(crate)));
        setItem(24, new GUIItem(22, plugin.getManager(CrateManager.class).getRandomItem(crate)));
    }

    private void giveRewards(CrateItem crateItem) {
        plugin.getManager(CrateManager.class).giveReward(crateItem, getViewer(), crate.getName(), crate);
    }

    public void fillBackground(Material m, String name, boolean showRewardsPointer) {
        int index = 0;
        while (index < getInventory().getSize()) {
            if (index != 22)
                this.setItem(index, new GUIItem(index, m, name));
            index++;
        }
        if (showRewardsPointer) {
            this.setItem(13, new GUIItem(13, customizationManager.parseMaterial(CustomizationOption.CSGO_ANIMATION_POINTER_MATERIAL), customizationManager.parseString(CustomizationOption.CSGO_ANIMATION_POINTER_NAME)));
            this.setItem(31, new GUIItem(31, customizationManager.parseMaterial(CustomizationOption.CSGO_ANIMATION_POINTER_MATERIAL), customizationManager.parseString(CustomizationOption.CSGO_ANIMATION_POINTER_NAME)));
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
