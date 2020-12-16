package com.kyozm.nanoutils.mixins;

import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.InputUtils;
import com.kyozm.nanoutils.utils.MapUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

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

            if (MapPreview.tooltipStack != null && itemStack == MapPreview.tooltipStack && MapPreview.revealItemOnHover.getVal()) {
                MapPreview.tooltipStack = null;
                return;
            }

            if (InputUtils.isKeybindHeld(MapPreview.hideStackDisplay.getVal()))
                return;

            if (MapUtils.renderMapFromStack(itemStack, x, y, 0.29f, MapPreview.cache.getVal())) { // do not question the float magick
                callbackInfo.cancel();
                if (MapPreview.drawStackQuantity.getVal()) {
                    if (itemStack.getCount() > 1) {
                        String count = String.valueOf(itemStack.getCount());
                        FontDrawer.drawString(count, x + 19 - FontDrawer.getStringWidth(count), y + 22 - FontDrawer.getFontHeight(), Color.DARK_GRAY );
                        FontDrawer.drawString(count, x + 18 - FontDrawer.getStringWidth(count), y + 21 - FontDrawer.getFontHeight(), Color.WHITE );
                    }
                }
            }
        }
    }
}
