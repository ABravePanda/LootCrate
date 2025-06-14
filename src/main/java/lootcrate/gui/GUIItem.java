package lootcrate.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class GUIItem {

    private int slot;
    private ItemStack itemStack;
    private final Map<ClickType, Consumer<ClickContext>> clickHandlers;
    private CancelPolicy cancelPolicy;

    private GUIItem(Builder builder) {
        this.slot = builder.slot;
        this.itemStack = builder.itemStack;
        this.clickHandlers = builder.clickHandlers != null ? builder.clickHandlers : new EnumMap<>(ClickType.class);
        this.cancelPolicy = builder.cancelPolicy != null ? builder.cancelPolicy : CancelPolicy.ALWAYS;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static GUIItem of(ItemStack stack, int slot) {
        return GUIItem.builder().itemStack(stack).slot(slot).build();
    }

    public GUIItem onClick(ClickType type, Consumer<ClickContext> handler) {
        this.clickHandlers.put(type, handler);
        return this;
    }

    public void handleClick(ClickContext ctx) {
        if (ctx == null || ctx.getRawEvent() == null) return;

        ClickType click = ctx.getRawEvent().getClick();
        Consumer<ClickContext> handler = clickHandlers.get(click);
        if (handler != null) {
            handler.accept(ctx);
        }
    }

    public boolean shouldCancel(ClickContext ctx) {
        return cancelPolicy.shouldCancel(ctx);
    }

    // Getters and setters

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Map<ClickType, Consumer<ClickContext>> getClickHandlers() {
        return clickHandlers;
    }

    public CancelPolicy getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(CancelPolicy cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    // --------- Builder Class ----------
    public static class Builder {
        private int slot;
        private ItemStack itemStack;
        private Map<ClickType, Consumer<ClickContext>> clickHandlers = new EnumMap<>(ClickType.class);
        private CancelPolicy cancelPolicy = CancelPolicy.ALWAYS;

        public Builder slot(int slot) {
            this.slot = slot;
            return this;
        }

        public Builder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public Builder cancelPolicy(CancelPolicy cancelPolicy) {
            this.cancelPolicy = cancelPolicy;
            return this;
        }

        public Builder onClick(ClickType type, Consumer<ClickContext> handler) {
            this.clickHandlers.put(type, handler);
            return this;
        }

        public Builder clickHandlers(Map<ClickType, Consumer<ClickContext>> handlers) {
            this.clickHandlers = handlers;
            return this;
        }

        public GUIItem build() {
            return new GUIItem(this);
        }
    }
}
