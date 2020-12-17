package com.kyozm.nanoutils.events;

import net.minecraft.inventory.Slot;

public class DrawSlotEvent extends NanoEvent {

    public Slot s;

    public DrawSlotEvent(Slot s) {
        this.s = s;
    }
}
