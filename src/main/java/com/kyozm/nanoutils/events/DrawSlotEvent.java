package com.kyozm.nanoutils.events;

import me.zero.alpine.event.type.Cancellable;
import net.minecraft.inventory.Slot;

public class DrawSlotEvent extends Cancellable {

    public Slot s;

    public DrawSlotEvent(Slot s) {
        this.s = s;
    }
}
