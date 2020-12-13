package com.kyozm.nanoutils.mixins;

import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.utils.DebugUtil;
import com.kyozm.nanoutils.utils.MapUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiContainer.class, priority = Integer.MAX_VALUE)
public class MixinGuiContainer {

    @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
    public void drawSlot(Slot slot, CallbackInfo callbackInfo) {
        if (!slot.getHasStack())
            return;
        ItemStack itemStack = slot.getStack();
        if (MapPreview.itemStackDisplay.getVal() && itemStack.getItem() instanceof ItemMap) {
            int x = slot.xPos -1;
            int y = slot.yPos - 1;

            if (MapUtils.renderMapFromStack(itemStack, x, y, 0.29f, MapPreview.cache.getVal())) { // do not question the float magick
                callbackInfo.cancel();
            }
        }
    }
}
