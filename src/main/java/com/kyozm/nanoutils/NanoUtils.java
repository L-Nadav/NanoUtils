package com.kyozm.nanoutils;

import com.kyozm.nanoutils.gui.NanoGui;
import com.kyozm.nanoutils.listeners.EventProcessor;
import com.kyozm.nanoutils.listeners.ModuleManagerDriver;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.utils.Keybinds;
import com.kyozm.nanoutils.utils.ShutdownHook;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = NanoUtils.MODID, name = NanoUtils.NAME, version = NanoUtils.VERSION)
public class NanoUtils
{
    public static final String MODID = "nanoutils";
    public static final String NAME = "NanoUtils";
    public static final String VERSION = "0.2";

    public static Logger logger;
    public static NanoGui gui = new NanoGui();
    public static ScaledResolution sr = null;
    public static final EventBus EVENT_BUS = new EventManager();
    EventProcessor eventProcessor;

    @Mod.Instance
    private static NanoUtils INSTANCE;

    public NanoUtils(){
        INSTANCE = this;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        Keybinds.register();
        ModuleManager.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        MinecraftForge.EVENT_BUS.register(new ModuleManagerDriver());
        logger.info("NanoUtils Initialized!");
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }

    public static NanoUtils getInstance(){
        return INSTANCE;
    }
}
