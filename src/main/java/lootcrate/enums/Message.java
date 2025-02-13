package lootcrate.enums;

public enum Message {
    PREFIX("prefix"),
    CHANCE("chance"),
    NO_PERMISSION_COMMAND("command-no-permission"),
    NO_PERMISSION_LOOTCRATE_INTERACT("lootcrate-interact-no-permission"),
    CANNOT_PLACE_LOOTKEY("cannot-place-lootkey"),
    JOIN_KEY_NOTIFICATION("join-key-notification"),
    MUST_BE_PLAYER("must-be-player"),
    MUST_BE_CONSOLE("must-be-console"),
    META_USAGE("meta-command-usage"),
    MUST_HOLD_ITEM("must-be-holding-item"),
    LOOTCRATE_CHANCE_NOT_100("lootcrate-chance-not-100"),
    LOOTCRATE_BASIC_USAGE("lootcrate-command-basic-usage"),
    LOOTCRATE_NOT_FOUND("lootcrate-not-found"),
    KEY_NOT_FOUND("key-not-found"),
    PLAYER_NOT_FOUND("player-not-found"),
    ENCHANTMENT_NOT_FOUND("enchantment-not-found"),
    LOOTCRATE_ITEM_NOT_FOUND("lootcrate-item-not-found"),
    INVENTORY_FULL("inventory-full"),
    LOOTCRATE_COMMAND_CREATE_USAGE("lootcrate-command-create-usage"),
    LOOTCRATE_COMMAND_CREATE_SUCCESS("lootcrate-command-create-success"),
    LOOTCRATE_COMMAND_CREATE_PERMISSIONS("loocrate-command-create-permissions"),
    LOOTCRATE_COMMAND_KEY_USAGE("lootcrate-command-key-usage"),
    LOOTCRATE_COMMAND_KEY_SUCCESS("lootcrate-command-key-success"),
    LOOTCRATE_COMMAND_ADD_USAGE("lootcrate-command-add-usage"),
    LOOTCRATE_COMMAND_ADD_MINMAX("lootcrate-command-add-minmax"),
    LOOTCRATE_COMMAND_ADD_SUCCESS("lootcrate-command-add-success"),
    LOOTCRATE_COMMAND_ADD_HINT("loocrate-command-add-hint"),
    LOOTCRATE_COMMAND_REMOVE_USAGE("lootcrate-command-remove-usage"),
    LOOTCRATE_COMMAND_REMOVE_SUCCESS("lootcrate-command-remove-success"),
    LOOTCRATE_COMMAND_ITEMS_USAGE("lootcrate-command-items-usage"),
    LOOTCRATE_COMMAND_ITEMS_FORMAT("lootcrate-command-items-format"),
    LOOTCRATE_COMMAND_LIST_USAGE("lootcrate-command-list-usage"),
    LOOTCRATE_COMMAND_LIST_FORMAT("lootcrate-command-list-format"),
    LOOTCRATE_COMMAND_SET_USAGE("lootcrate-command-set-usage"),
    LOOTCRATE_COMMAND_SET_SUCCESS("lootcrate-command-set-success"),
    LOOTCRATE_COMMAND_SET_FAILURE("lootcrate-command-set-failure"),
    LOOTCRATE_COMMAND_SET_REMOVE_SUCCESS("lootcrate-command-set-remove-success"),
    LOOTCRATE_COMMAND_GIVE_USAGE("lootcrate-command-give-usage"),
    LOOTCRATE_COMMAND_GIVE_SUCCESS_RECEIVER("lootcrate-command-give-success-to-receiver"),
    LOOTCRATE_COMMAND_GIVE_SUCCESS_SENDER("lootcrate-command-give-success-to-sender"),
    LOOTCRATE_COMMAND_CLAIM_FULL_INVENTORY("lootcrate-command-claim-full-inventory"),
    LOOTCRATE_COMMAND_CLAIM_SUCCESS("lootcrate-command-claim-success"),
    LOOTCRATE_COMMAND_COMMAND_USAGE("lootcrate-command-command-usage"),
    LOOTCRATE_COMMAND_COMMAND_SUCCESS("lootcrate-command-command-success"),
    LOOTCRATE_COMMAND_RELOAD_SUCCESS("lootcrate-command-reload-success"),
    LOOTCRATE_COMMAND_DISPLAY_SUCCESS("lootcrate-command-displaychances-success"),
    LOOTCRATE_COMMAND_DISPLAY_USAGE("lootcrate-command-displaychances-usage"),
    LOOTCRATE_COMMAND_PREVIEW_USAGE("lootcrate-command-preview-usage"),
    LOOTCRATE_COMMAND_PREVIEW_SUCCESS("lootcrate-command-preview-success"),
    LOOTCRATE_COMMAND_VERION_USAGE("lootcrate-command-version-usage"),
    LOOTCRATE_COMMAND_DELETE_USAGE("lootcrate-command-delete-usage"),
    LOOTCRATE_COMMAND_DELETE_SUCCESS("lootcrate-command-delete-success"),
    LOOTCRATE_COMMAND_OPTION_USAGE("lootcrate-command-option-usage"),
    MESSAGE_COMMAND_FORMAT("message-command-format"),
    MESSAGE_COMMAND_USAGE("message-command-usage"),
    LOOTCRATE_OPEN("lootcrate-open"),
    LOOTCRATE_COOLDOWN_IN_EFFECT("lootcrate-cooldown-in-effect"),
    LOOTCRATE_INCORRECT_KEY("lootcrate-wrong-key"),

    LOOTCRATE_CHANGE_CRATE_NAME("lootcrate-change-crate-name"),
    LOOTCRATE_CHANGE_CRATE_MESSAGE("lootcrate-change-crate-message"),
    LOOTCRATE_CREATE_CRATE_NAME("lootcrate-create-crate-name"),
    LOOTCRATE_ADD_ITEM_COMMAND("lootcrate-add-item-command"),
    LOOTCRATE_CHANGE_CRATE_KNOCKBACK("lootcrate-change-crate-knockback"),
    LOOTCRATE_CHANGE_CRATE_COOLDOWN("lootcrate-change-crate-cooldown"),
    LOOTCRATE_ACTION_CANCELED("lootcrate-action-canceled"),

    LOOTCRATE_NAME_CHANGED("lootcrate-name-changed"),
    LOOTCRATE_MESSAGE_CHANGED("lootcrate-message-changed"),
    LOOTCRATE_KNOCKBACK_CHANGED("lootcrate-knockback-changed"),
    LOOTCRATE_KNOCKBACK_NOT_CHANGED("lootcrate-knockback-not-changed"),
    LOOTCRATE_COOLDOWN_CHANGED("lootcrate-cooldown-changed"),
    LOOTCRATE_COOLDOWN_NOT_CHANGED("lootcrate-cooldown-not-changed"),
    LOOTCRATE_SOUND_CHANGED("lootcrate-changed-sound-message");

    String key;

    Message(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
