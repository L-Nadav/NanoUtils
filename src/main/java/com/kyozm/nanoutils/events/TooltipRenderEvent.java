package com.kyozm.nanoutils.events;

import net.minecraft.item.ItemStack;

public class TooltipRenderEvent extends NanoEvent {
    public int mouseX;
    public int mouseY;
    public ItemStack stack;

    public TooltipRenderEvent(int mouseX, int mouseY, ItemStack stack) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.stack = stack;
    }
}
