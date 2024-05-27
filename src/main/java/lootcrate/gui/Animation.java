package lootcrate.gui;

public interface Animation {

    void play();
    void stop();
    void tick();
    int getDurationTicks();
    int getTicksRemaining();

}
