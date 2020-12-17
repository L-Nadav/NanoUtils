package com.kyozm.nanoutils.mixins;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.events.DrawSlotEvent;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiContainer.class, priority = Integer.MAX_VALUE)
public class MixinGuiContainer {

    @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
    public void drawSlot(Slot slot, CallbackInfo callbackInfo) {
        DrawSlotEvent evt = new DrawSlotEvent(slot);
        NanoUtils.EVENT_BUS.post(evt);
        if (evt.isCancelled())
            callbackInfo.cancel();
    }

}
