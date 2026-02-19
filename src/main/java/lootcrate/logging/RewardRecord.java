package lootcrate.logging;

import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

import java.nio.ByteBuffer;
import java.util.UUID;

public class RewardRecord {

    // 16 bytes UUID + 4 * 4-byte ints = 32
    public static final int BYTE_SIZE = 16 + 4 + 4 + 4 + 4; // 32

    private final UUID playerUUID;
    private final int crateId;
    private final int rewardId;
    private final int rewardAmount;
    private final int timestamp; // epoch seconds

    public RewardRecord(UUID playerUUID, int crateId, int rewardId, int rewardAmount, int timestamp) {
        this.playerUUID = playerUUID;
        this.crateId = crateId;
        this.rewardId = rewardId;
        this.rewardAmount = rewardAmount;
        this.timestamp = timestamp;
    }

    public RewardRecord(UUID playerUUID, int crateId, int rewardId, int rewardAmount) {
        this(playerUUID, crateId, rewardId, rewardAmount, (int) (System.currentTimeMillis() / 1000));
    }

    public RewardRecord(UUID playerUUID, Crate crate, CrateItem reward, int rewardAmount) {
        this(playerUUID, crate.getId(), reward.getId(), rewardAmount, (int) (System.currentTimeMillis() / 1000));
    }

    public UUID getPlayerUUID() { return playerUUID; }
    public int getCrateId() { return crateId; }
    public int getRewardId() { return rewardId; }
    public int getRewardAmount() { return rewardAmount; }
    public int getTimestamp() { return timestamp; }

    /** Serialize to fixed 32-byte array */
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(BYTE_SIZE);

        buffer.putLong(playerUUID.getMostSignificantBits());
        buffer.putLong(playerUUID.getLeastSignificantBits());
        buffer.putInt(crateId);
        buffer.putInt(rewardId);
        buffer.putInt(rewardAmount);
        buffer.putInt(timestamp);

        return buffer.array();
    }

    /** Deserialize from fixed 32-byte array */
    public static RewardRecord fromBytes(byte[] bytes) {
        if (bytes.length != BYTE_SIZE) {
            throw new IllegalArgumentException("Invalid RewardRecord byte array length: " + bytes.length);
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        long most = buffer.getLong();
        long least = buffer.getLong();
        int crateId = buffer.getInt();
        int rewardId = buffer.getInt();
        int rewardAmount = buffer.getInt();
        int timestamp = buffer.getInt();

        return new RewardRecord(new UUID(most, least), crateId, rewardId, rewardAmount, timestamp);
    }
}
