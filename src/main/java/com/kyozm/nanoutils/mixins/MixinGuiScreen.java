package com.kyozm.nanoutils.mixins;

import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.modules.render.ShulkerPreview;
import com.kyozm.nanoutils.utils.MapUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
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
        MapPreview.onTooltip(stack, x, y, callback);
        ShulkerPreview.onTooltip(stack, x, y, callback);
    }


}