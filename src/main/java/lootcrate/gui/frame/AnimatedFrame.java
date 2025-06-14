package lootcrate.gui.frame;

public interface AnimatedFrame extends GuiFrame {
    @Override
    default void tick() {}
}
