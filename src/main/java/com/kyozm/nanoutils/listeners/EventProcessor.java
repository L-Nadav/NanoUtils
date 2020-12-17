package com.kyozm.nanoutils.listeners;

import com.kyozm.nanoutils.NanoUtils;
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

public class EventProcessor {
    public static EventProcessor INSTANCE;
    public EventProcessor(){
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onGameOverlay(RenderGameOverlayEvent.Text event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRespawn(PlayerEvent.PlayerRespawnEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onHighlight(DrawBlockHighlightEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event){ NanoUtils.EVENT_BUS.post(event); }

    @SubscribeEvent
    public void onDamage(LivingDamageEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event){
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event){ NanoUtils.EVENT_BUS.post(event);}

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event) {
        NanoUtils.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onGuiScreen(GuiScreenEvent.DrawScreenEvent.Post event) { NanoUtils.EVENT_BUS.post(event); }

    public void init(){
        NanoUtils.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
