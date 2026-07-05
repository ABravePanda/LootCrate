package lootcrate.core.ports;

public interface TaskScheduler {
    void runTaskAsynchronously(Runnable task);
}
