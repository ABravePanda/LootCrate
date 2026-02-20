package lootcrate.gui.frame;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Base frame for GUI animations that progress over time.
 */
public abstract class AbstractAnimatedFrame extends AbstractGuiFrame implements AnimatedFrame {

    private final int durationTicks;
    private final int tickRate;

    private boolean animationRunning;
    private int elapsedTicks;

    protected AbstractAnimatedFrame(JavaPlugin plugin, int size, String title, Player viewer, int durationTicks, int tickRate) {
        super(plugin, size, title, viewer);
        this.durationTicks = Math.max(1, durationTicks);
        this.tickRate = Math.max(1, tickRate);
    }

    @Override
    public void onOpen() {
        startAnimation();
    }

    @Override
    public void onClose() {
        stopAnimation();
    }

    @Override
    public void tick() {
        if (!animationRunning) {
            return;
        }

        elapsedTicks++;

        if (elapsedTicks % tickRate == 0) {
            onAnimationTick(elapsedTicks / tickRate);
        }

        if (elapsedTicks >= durationTicks) {
            animationRunning = false;
            onAnimationComplete();
        }
    }

    @Override
    public void startAnimation() {
        this.animationRunning = true;
        this.elapsedTicks = 0;
    }

    @Override
    public void stopAnimation() {
        this.animationRunning = false;
    }

    @Override
    public boolean isAnimationRunning() {
        return animationRunning;
    }

    protected abstract void onAnimationTick(int animationStep);

    protected abstract void onAnimationComplete();
}
