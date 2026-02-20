package lootcrate.gui.frame;

public interface AnimatedFrame extends GuiFrame {

    void startAnimation();

    void stopAnimation();

    boolean isAnimationRunning();
}
