package lootcrate.enums;

public enum Option {
    DISPATCH_COMMAND_ITEM_AMOUNT("dispatch-command-item-time", DataType.BOOLEAN),
    ADMIN_NOTIFICATIONS("admin-update-notification", DataType.BOOLEAN),
    LOOTCRATE_CLAIM_ENABLED("lootcrate-claim-enabled", DataType.BOOLEAN),
    ALLOW_VIRTUAL_KEYS("allow-virtual-keys", DataType.BOOLEAN),
    PRIORITIZE_INVENTORY_OVER_CLAIM("prioritize-inventory-over-claim", DataType.BOOLEAN),
    JOIN_KEY_NOTIFICATION("join-key-notification", DataType.BOOLEAN),
    BROADCAST_ITEM_WIN_ENABLED("broadcast-item-win-enabled", DataType.BOOLEAN),
    BROADCAST_ITEM_WIN_MESSAGE("broadcast-item-win-message", DataType.STRING);

    String key;
    DataType type;

    Option(String key, DataType type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public DataType getType() {
        return this.type;
    }
}
