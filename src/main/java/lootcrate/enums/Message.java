package lootcrate.enums;

public enum Message {
    PREFIX("prefix", "&e&lLOOTCRATE &r"),
    CHANCE("chance", "&6&lChance: &e{item_chance}%"),
    NO_PERMISSION_COMMAND("command-no-permission", "&cYou have no permission to do this."),
    NO_PERMISSION_LOOTCRATE_INTERACT("lootcrate-interact-no-permission","&cYou do not have permission to interact with &e{crate_name}&c."),
    CANNOT_PLACE_LOOTKEY("cannot-place-lootkey", "&cYou cannot place a Loot Key."),
    JOIN_KEY_NOTIFICATION("join-key-notification", "&fYou still have &6{key_amount}&f keys to claim. Use &6/lootcrate claim&f to claim them now"),
    MUST_BE_PLAYER("must-be-player", "&cYou must be a player to do this."),
    MUST_BE_CONSOLE("must-be-console", "&cYou must be the console to do this."),
    META_USAGE("meta-command-usage","&cUsage: /meta <name | lore | enchantment | all>"),
    MUST_HOLD_ITEM("must-be-holding-item","&cYou must be holding an item in your main hand!"),
    LOOTCRATE_CHANCE_NOT_100("lootcrate-chance-not-100", "&4Error: &cThe items in the LootCrate &e{crate_name} &conly total &e{crate_total_chance}&c."),
    LOOTCRATE_BASIC_USAGE("lootcrate-command-basic-usage", "&cUsage: /lootcrate <create | delete | key | set | gui | add | remove | give | items | list | reload>"),
    LOOTCRATE_NOT_FOUND("lootcrate-not-found","&cThe LootCrate with the id &e#{crate_id}&c was not found."),
    KEY_NOT_FOUND("key-not-found", "&cThe key for the LootCrate &e{crate_name}&c was not found."),
    PLAYER_NOT_FOUND("player-not-found", "&cCould not find player &e{player_name}&c."),
    ENCHANTMENT_NOT_FOUND("enchantment-not-found", "&cCould not find enchantment &e{enchantment_name}&c."),
    LOOTCRATE_ITEM_NOT_FOUND("lootcrate-item-not-found", "&cThe item &e#{item_id} &cin LootCrate with the id &e#{crate_id}&c was not found."),
    INVENTORY_FULL("inventory-full", "&cYour inventory is full!"),
    LOOTCRATE_COMMAND_CREATE_USAGE("lootcrate-command-create-usage", "&cUsage: /lootcrate create [name]"),
    LOOTCRATE_COMMAND_CREATE_SUCCESS("lootcrate-command-create-success", "&fCreated a LootCrate with the name &e{crate_name}&f. &fReference ID: &e#{crate_id}"),
    LOOTCRATE_COMMAND_CREATE_PERMISSIONS("loocrate-command-create-permissions", "&6To allow players to use this crate, give them the permission &flootcrate.interact.{crate_id}&6 or &flootcrate.interact.*&6."),
    LOOTCRATE_COMMAND_KEY_USAGE("lootcrate-command-key-usage", "&cUsage: /lootcrate key [crate id] [glowing]"),
    LOOTCRATE_COMMAND_KEY_SUCCESS("lootcrate-command-key-success", "&fAdded a key to the LootCrate with the name &e{crate_name}&f."),
    LOOTCRATE_COMMAND_ADD_USAGE("lootcrate-command-add-usage", "&cUsage: /lootcrate add [crate id] [min amount] [max amount] [chance] [isDisplay]"),
    LOOTCRATE_COMMAND_ADD_MINMAX("lootcrate-command-add-minmax", "&cThe minimum amount must be smaller or equal to the max amount"),
    LOOTCRATE_COMMAND_ADD_SUCCESS("lootcrate-command-add-success", "&fAdded &e{item_type}&f to the LootCrate with the name &e{crate_name}&f. The item's id is &e#{item_id}&f."),
    LOOTCRATE_COMMAND_ADD_HINT("loocrate-command-add-hint", "&6If you want this item to only execute commands, &aisDisplay = true&6."),
    LOOTCRATE_COMMAND_REMOVE_USAGE("lootcrate-command-remove-usage", "&cUsage: /lootcrate remove [crate id] [item id]"),
    LOOTCRATE_COMMAND_REMOVE_SUCCESS("lootcrate-command-remove-success", "&fRemoved item &e#{item_id}&f from the LootCrate with the name &e{crate_name}&f."),
    LOOTCRATE_COMMAND_ITEMS_USAGE("lootcrate-command-items-usage", "&cUsage: /lootcrate items [crate id]"),
    LOOTCRATE_COMMAND_ITEMS_FORMAT("lootcrate-command-items-format", "&7#{item_id}&f | Type: &e{item_type}&f | Name: &e{item_name}&f | MinAmount: &e{item_min_amount}&f | MaxAmount: &e{item_max_amount}&f | Chance: &e{item_chance}&f | Commands: &e{item_commands}&f"),
    LOOTCRATE_COMMAND_LIST_USAGE("lootcrate-command-list-usage", "&cUsage: /lootcrate list"),
    LOOTCRATE_COMMAND_LIST_FORMAT("lootcrate-command-list-format", "&7#{crate_id}&f | Name: &e{crate_name}&f | Item Count: &e{crate_item_count}&f | Key Type: &e{crate_key_type}&f"),
    LOOTCRATE_COMMAND_SET_USAGE("lootcrate-command-set-usage", "&cUsage: /lootcrate set [crate id]"),
    LOOTCRATE_COMMAND_SET_SUCCESS("lootcrate-command-set-success", "&fSet &e{X}, {Y}, {Z} &fas a location for the LootCrate &e{crate_name}&f."),
    LOOTCRATE_COMMAND_SET_FAILURE("lootcrate-command-set-failure", "&cA crate already exists at this location!"),
    LOOTCRATE_COMMAND_UNSET_USAGE("lootcrate-command-unset-usage", "&cUsage: /lootcrate unset"),
    LOOTCRATE_COMMAND_UNSET_SUCCESS("lootcrate-command-unset-success","&fRemoved &e{X}, {Y}, {Z} &fas a location for the LootCrate &e{crate_name}&f."),
    LOOTCRATE_COMMAND_UNSET_FAILURE("lootcrate-command-unset-failure","&cThis location does not have a crate!"),
    LOOTCRATE_COMMAND_GIVE_USAGE("lootcrate-command-give-usage","&cUsage: /lootcrate give [player name] [crate id] (amount)"),
    LOOTCRATE_COMMAND_GIVE_SUCCESS_RECEIVER("lootcrate-command-give-success-to-receiver","&fYou have received {key_amount} key(s) for the LootCrate &e{crate_name}&f. Use &6/lootcrate claim&f to claim it now."),
    LOOTCRATE_COMMAND_GIVE_SUCCESS_SENDER("lootcrate-command-give-success-to-sender","&fYou have sent {key_amount} key(s) for the LootCrate &e{crate_name}&f to &e{player_name}&f."),
    LOOTCRATE_COMMAND_CLAIM_FULL_INVENTORY("lootcrate-command-claim-full-inventory","&cYou cannot claim this key as your inventory is full."),
    LOOTCRATE_COMMAND_CLAIM_SUCCESS("lootcrate-command-claim-success","&fYou have claimed a key for &6{crate_name}&f."),
    LOOTCRATE_COMMAND_COMMAND_USAGE("lootcrate-command-command-usage","&cUsage: /lootcrate command [crate id] [item id] [command]"),
    LOOTCRATE_COMMAND_COMMAND_SUCCESS("lootcrate-command-command-success","&fYou have added a command to Item &e#{item_id} for the LootCrate &e{crate_name}&f."),
    LOOTCRATE_COMMAND_RELOAD_SUCCESS("lootcrate-command-reload-success","&aPlugin has been reloaded."),
    LOOTCRATE_COMMAND_DISPLAY_SUCCESS("lootcrate-command-displaychances-success","&fYou have updated &e{crate_name}&f's option to display chances to: &e{value}&f"),
    LOOTCRATE_COMMAND_DISPLAY_USAGE("lootcrate-command-displaychances-usage","&cUsage: /lootcrate displaychances [crate id] [display chances]"),
    LOOTCRATE_COMMAND_PREVIEW_USAGE("lootcrate-command-preview-usage","&cUsage: /lootcrate preview [crate_id]"),
    LOOTCRATE_COMMAND_PREVIEW_SUCCESS("lootcrate-command-preview-success","&aYou are now viewing &e{crate_name}"),
    LOOTCRATE_COMMAND_VERION_USAGE("lootcrate-command-version-usage","&cUsage: /lootcrate version"),
    LOOTCRATE_COMMAND_DELETE_USAGE("lootcrate-command-delete-usage","&cUsage: /lootcrate delete [crate id]"),
    LOOTCRATE_COMMAND_DELETE_SUCCESS("lootcrate-command-delete-success","&fDeleted the LootCrate with the name &e{crate_name}&f."),
    MESSAGE_COMMAND_FORMAT("message-command-format","&f{message}"),
    MESSAGE_COMMAND_USAGE("message-command-usage","&cUsage: /message {player} {message}" ),
    LOOTCRATE_OPEN("lootcrate-open","&aOpening LootCrate &e{crate_name}"),
    LOOTCRATE_INCORRECT_KEY("lootcrate-wrong-key","&cThis is the wrong key to open the LootCrate &e{crate_name}"),
    LOOTCRATE_VIEW_CLOSE_ITEM("lootcrate-view-close-item","&cClose"),
    LOOTCRATE_VIEW_BLOCKER_ITEM("lootcrate-view-blocker-item","&k-"),
    LOOTCRATE_VIEW_NEXT_ITEM("lootcrate-view-next-item","&6Next"),
    LOOTCRATE_VIEW_PREV_ITEM("lootcrate-view-prev-item","&6Prev");

    String key;
    String defaultValue;

    Message(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return this.key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
