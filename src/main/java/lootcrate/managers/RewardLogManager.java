package lootcrate.logging;

import lootcrate.LootCrate;
import lootcrate.managers.BasicManager;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RewardLogManager extends BasicManager {

    private File logFile;
    private FileChannel channel;
    private final Object writeLock = new Object();

    private ExecutorService ioExecutor;

    public RewardLogManager(LootCrate plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        try {
            File dataFolder = getPlugin().getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            logFile = new File(dataFolder, "reward-log.bin");

            channel = FileChannel.open(
                    logFile.toPath(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND
            );

            // single-threaded executor so all IO is serialized and off the main thread
            ioExecutor = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r, "LootCrate-RewardLog-IO");
                t.setDaemon(true);
                return t;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
        if (ioExecutor != null) {
            ioExecutor.shutdownNow();
        }

        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* ======================
     *   WRITE – ASYNC API
     * ====================== */

    /**
     * Append a record to the log file asynchronously.
     * This never blocks the calling thread.
     */
    public CompletableFuture<Void> logAsync(RewardRecord record) {
        if (channel == null || ioExecutor == null) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.runAsync(() -> writeRecord(record), ioExecutor);
    }

    // Internal sync write, only executed on ioExecutor
    private void writeRecord(RewardRecord record) {
        byte[] data = record.toBytes();
        ByteBuffer buffer = ByteBuffer.wrap(data);

        synchronized (writeLock) {
            try {
                channel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* ======================
     *   READ – ASYNC API
     * ====================== */

    /** Read all records asynchronously. */
    public CompletableFuture<List<RewardRecord>> readAllAsync() {
        if (logFile == null || ioExecutor == null) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        return CompletableFuture.supplyAsync(this::readAllInternal, ioExecutor);
    }

    /** Read all records for a specific player asynchronously. */
    public CompletableFuture<List<RewardRecord>> readForPlayerAsync(UUID uuid) {
        return readAllAsync().thenApply(all -> {
            List<RewardRecord> filtered = new ArrayList<>();
            for (RewardRecord record : all) {
                if (record.getPlayerUUID().equals(uuid)) {
                    filtered.add(record);
                }
            }
            return filtered;
        });
    }

    /**
     * Get the last (newest) reward record for a player asynchronously.
     * Uses Optional because there might be no records.
     */
    public CompletableFuture<Optional<RewardRecord>> readLastForPlayerAsync(UUID uuid) {
        return readForPlayerAsync(uuid).thenApply(list -> {
            if (list.isEmpty()) {
                return Optional.empty();
            }
            // sort by timestamp if you want to be safe
            list.sort(Comparator.comparingInt(RewardRecord::getTimestamp));
            return Optional.of(list.get(list.size() - 1));
        });
    }

    /* ======================
     *   READ – INTERNAL SYNC
     * ====================== */

    // Only run on ioExecutor, never from main thread
    private List<RewardRecord> readAllInternal() {
        List<RewardRecord> records = new ArrayList<>();
        if (logFile == null || !logFile.exists()) {
            return records;
        }

        try (FileChannel readChannel = FileChannel.open(logFile.toPath(), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(RewardRecord.BYTE_SIZE);

            while (true) {
                buffer.clear();
                int read = readChannel.read(buffer);
                if (read == -1) {
                    break; // EOF
                }
                if (read < RewardRecord.BYTE_SIZE) {
                    // Partial / corrupt record at end – ignore it
                    break;
                }

                buffer.flip();
                byte[] data = new byte[RewardRecord.BYTE_SIZE];
                buffer.get(data);

                RewardRecord record = RewardRecord.fromBytes(data);
                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
}
