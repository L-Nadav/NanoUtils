package com.kyozm.nanoutils.listeners;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ModuleManagerDriver {

    @SubscribeEvent()
    public void ticker(TickEvent.ClientTickEvent e) {
        ModuleManager.clientTick();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (event.isCanceled())
            return;

        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && !(mc.player.getRidingEntity() instanceof AbstractHorse && !mc.player.isCreative())) {
            NanoUtils.sr = new ScaledResolution(mc);
            ModuleManager.onRender();
        } else if (mc.player.getRidingEntity() instanceof AbstractHorse && !mc.player.isCreative()) {
            NanoUtils.sr = new ScaledResolution(mc);
            ModuleManager.onRender();
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        ModuleManager.keyInput(event);
    }


}
