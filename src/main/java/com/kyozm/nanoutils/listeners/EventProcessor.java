package com.kyozm.nanoutils.listeners;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.modules.render.ShulkerPreview;
import com.kyozm.nanoutils.utils.Clipboard;
import com.kyozm.nanoutils.utils.Config;
import com.kyozm.nanoutils.utils.InputUtils;
import com.kyozm.nanoutils.utils.MapUtils;
import com.kyozm.nanoutils.utils.TickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.storage.MapData;
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
import org.lwjgl.input.Keyboard;

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
    public void onLivingDeath(LivingDeathEvent event){ NanoUtils.EVENT_BUS.post(event);}

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
        ShulkerPreview.drawTooltip();
        MapPreview.drawTooltip();
    }

    public void init(){
        NanoUtils.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
