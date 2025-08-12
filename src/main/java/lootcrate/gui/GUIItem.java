package lootcrate.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents an interactive item placed in a custom GUI inventory.
 * <p>
 * A {@code GUIItem} binds:
 * <ul>
 *   <li>An {@link ItemStack} to display in a specific slot</li>
 *   <li>One or more click handlers bound to specific {@link ClickType}s</li>
 *   <li>A {@link CancelPolicy} to determine whether the click should cancel the Bukkit event</li>
 * </ul>
 * <p>
 * Instances should be created using the {@link Builder} or the {@link #of(ItemStack, int)} helper method.
 *
 * <pre>{@code
 * GUIItem item = GUIItem.builder()
 *     .slot(0)
 *     .itemStack(new ItemStack(Material.DIAMOND))
 *     .onClick(ClickType.LEFT, ctx -> ctx.getPlayer().sendMessage("You clicked me!"))
 *     .cancelPolicy(CancelPolicy.TOP_ONLY)
 *     .build();
 * }</pre>
 */
public class GUIItem {

    /** Slot index (0-based) in the inventory where the item will be placed. */
    private int slot;

    /** The visual representation of the item in the GUI. */
    private ItemStack itemStack;

    /** Maps click types to their associated click handlers. */
    private final Map<ClickType, Consumer<ClickContext>> clickHandlers;

    /** Determines whether the click event should be cancelled. */
    private CancelPolicy cancelPolicy;

    /**
     * Constructs a {@code GUIItem} from a {@link Builder}.
     *
     * @param builder the builder containing item configuration
     */
    private GUIItem(Builder builder) {
        this.slot = builder.slot;
        this.itemStack = builder.itemStack;
        this.clickHandlers = builder.clickHandlers != null
                ? builder.clickHandlers
                : new EnumMap<>(ClickType.class);
        this.cancelPolicy = builder.cancelPolicy != null
                ? builder.cancelPolicy
                : CancelPolicy.ALWAYS;
    }

    /**
     * Starts a new {@link Builder} for creating a {@code GUIItem}.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new {@code GUIItem} from an {@link ItemStack} and slot index
     * with default {@link CancelPolicy#ALWAYS} and no click handlers.
     *
     * @param stack the item to display
     * @param slot  the inventory slot index
     * @return a new {@code GUIItem} instance
     */
    public static GUIItem of(ItemStack stack, int slot) {
        return GUIItem.builder().itemStack(stack).slot(slot).build();
    }

    /**
     * Adds a click handler for a given click type.
     *
     * @param type    the {@link ClickType} to listen for
     * @param handler the handler to execute when clicked
     * @return this {@code GUIItem} instance for chaining
     */
    public GUIItem onClick(ClickType type, Consumer<ClickContext> handler) {
        this.clickHandlers.put(type, handler);
        return this;
    }

    /**
     * Handles a click event by invoking the appropriate handler, if present.
     *
     * @param ctx the {@link ClickContext} for the click event
     */
    public void handleClick(ClickContext ctx) {
        if (ctx == null || ctx.getRawEvent() == null) return;

        ClickType click = ctx.getRawEvent().getClick();
        Consumer<ClickContext> handler = clickHandlers.get(click);
        if (handler != null) {
            handler.accept(ctx);
        }
    }

    /**
     * Determines if the click event should be cancelled based on the {@link CancelPolicy}.
     *
     * @param ctx the {@link ClickContext} of the event
     * @return true if the event should be cancelled
     */
    public boolean shouldCancel(ClickContext ctx) {
        return cancelPolicy.shouldCancel(ctx);
    }

    // -------------------- Getters / Setters --------------------

    /** @return the inventory slot for this item */
    public int getSlot() {
        return slot;
    }

    /** @param slot the inventory slot for this item */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /** @return the visual {@link ItemStack} for this item */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /** @param itemStack the new {@link ItemStack} to display */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /** @return the map of click handlers for this item */
    public Map<ClickType, Consumer<ClickContext>> getClickHandlers() {
        return clickHandlers;
    }

    /** @return the {@link CancelPolicy} for this item */
    public CancelPolicy getCancelPolicy() {
        return cancelPolicy;
    }

    /** @param cancelPolicy the {@link CancelPolicy} to apply */
    public void setCancelPolicy(CancelPolicy cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    // -------------------- Builder --------------------

    /**
     * Fluent builder for constructing {@link GUIItem} instances.
     */
    public static class Builder {
        private int slot;
        private ItemStack itemStack;
        private Map<ClickType, Consumer<ClickContext>> clickHandlers = new EnumMap<>(ClickType.class);
        private CancelPolicy cancelPolicy = CancelPolicy.ALWAYS;

        /**
         * Sets the inventory slot for the item.
         *
         * @param slot the slot index
         * @return this builder
         */
        public Builder slot(int slot) {
            this.slot = slot;
            return this;
        }

        /**
         * Sets the {@link ItemStack} to display.
         *
         * @param itemStack the item stack
         * @return this builder
         */
        public Builder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        /**
         * Sets the cancel policy for this item.
         *
         * @param cancelPolicy the {@link CancelPolicy} to use
         * @return this builder
         */
        public Builder cancelPolicy(CancelPolicy cancelPolicy) {
            this.cancelPolicy = cancelPolicy;
            return this;
        }

        /**
         * Registers a click handler for a specific click type.
         *
         * @param type    the {@link ClickType} to listen for
         * @param handler the handler to run
         * @return this builder
         */
        public Builder onClick(ClickType type, Consumer<ClickContext> handler) {
            this.clickHandlers.put(type, handler);
            return this;
        }

        /**
         * Replaces all click handlers for this item.
         *
         * @param handlers a map of click types to handlers
         * @return this builder
         */
        public Builder clickHandlers(Map<ClickType, Consumer<ClickContext>> handlers) {
            this.clickHandlers = handlers;
            return this;
        }

        /**
         * Builds the {@link GUIItem} instance with the configured properties.
         *
         * @return a new {@link GUIItem}
         */
        public GUIItem build() {
            return new GUIItem(this);
        }
    }
}
