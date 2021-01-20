package lootcrate.other;

public enum Placeholder
{
    MESSAGE("message"),
    PLAYER("player"),
    PLAYER_NAME("player_name"),
    SENDER_NAME("sender_name"),
    CRATE_NAME("crate_name"),
    CRATE_ID("crate_id"),
    CRATE_ITEM_COUNT("crate_item_count"),
    CRATE_KEY_TYPE("crate_key_type"),
    ITEM_TYPE("item_type"),
    ITEM_NAME("item_name"),
    ITEM_ID("item_id"),
    ITEM_CHANCE("item_chance"),
    ITEM_MAX_AMOUNT("item_max_amount"),
    ITEM_MIN_AMOUNT("item_min_amount"),
    ITEM_COMMANDS("item_commands"),
    X("X"), Y("Y"), Z("Z"),
    TOTAL_CRATE_CHANCE("crate_total_chance"),
    ENCHANTMENT_NAME("enchantment_name"),
    VALUE("value");

    String key;
    
    Placeholder(String key)
    {
	this.key = key;
    }

    public String getKey()
    {
	return this.key;
    }
}
