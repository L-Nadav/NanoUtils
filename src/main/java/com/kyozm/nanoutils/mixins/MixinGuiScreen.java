package com.kyozm.nanoutils.mixins;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.events.TooltipRenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiScreen.class, priority = Integer.MAX_VALUE)
public class MixinGuiScreen {
    @Shadow public Minecraft mc;

    @Inject(method = "renderToolTip", at = @At("HEAD"), cancellable = true)
    public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo callback) {
        TooltipRenderEvent evt = new TooltipRenderEvent(x, y, stack);
        NanoUtils.EVENT_BUS.post(evt);
        if (evt.isCancelled())
            callback.cancel();
    }


}