package com.kyozm.nanoutils.listeners;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.utils.Config;
import com.kyozm.nanoutils.utils.MapUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.awt.*;

public class EventProcessor {
    public static EventProcessor INSTANCE;
    public EventProcessor(){
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event){ NanoUtils.EVENT_BUS.post(event); }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){
        NanoUtils.EVENT_BUS.post(event);}

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onGuiScreen(GuiScreenEvent event) {
        if (MapPreview.activeTooltip) {
            int x = MapPreview.tooltipX + 4;
            if (MapPreview.drawStackName.getVal())
                NanoUtils.gui.drawTooltip(String.format("x%s §o%s",  MapPreview.tooltipStack.getCount(), MapPreview.tooltipStack.getDisplayName()), x - 3, MapPreview.tooltipY - 2);
            int w = (int) (64 * MapPreview.tooltipScale.getVal());
            int h = (int) (64 * MapPreview.tooltipScale.getVal());
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            Gui.drawRect(x + 3, MapPreview.tooltipY + 3, x + 3 + w + 4, MapPreview.tooltipY + 5 + h + 2, MapPreview.tooltipBorder.getVal().getRGB());
            GlStateManager.disableDepth();
            GlStateManager.enableLighting();
            MapUtils.renderMapFromStack(MapPreview.tooltipStack, x + 5, MapPreview.tooltipY + 5, MapPreview.tooltipScale.getVal(), MapPreview.cache.getVal());
            MapPreview.activeTooltip = false;
        }
    }

    public void init(){
        NanoUtils.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
