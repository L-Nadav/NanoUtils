package com.kyozm.nanoutils.modules.gui;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.NanoGui;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleCategory;
import com.kyozm.nanoutils.settings.Setting;
import org.lwjgl.input.Keyboard;

public class NanoGuiModule extends Module {

    public NanoGuiModule() {
        name = "GUI";
        category = ModuleCategory.GUI;
        bind = Keyboard.KEY_NONE;
        desc = "GUI Config Module.";

        registerSetting(NanoGuiModule.class, showModuleDescription);
        registerSetting(NanoGuiModule.class, hoverOpen);
    }

    public static Setting<Boolean> showModuleDescription = new Setting<Boolean>()
            .setConfigName("GUIModuleDescriptions")
            .setName("Module Descriptions")
            .setDefaultVal(true)
            .withType(Boolean.class);

    public static Setting<Boolean> hoverOpen = new Setting<Boolean>()
            .setConfigName("GUIModuleHoverOpen")
            .setName("Hover Open")
            .setDefaultVal(true)
            .withType(Boolean.class);

    @Override
    public void onEnable() {
        disable();
        if (mc.currentScreen instanceof NanoGui) return;
        mc.displayGuiScreen(NanoUtils.gui);
    }

    @Override
    public void onRender() {
        NanoUtils.gui.widgets.stream().filter(w -> !w.clearable).forEach(Widget::render);
        NanoUtils.gui.updateQueues();
    }
}
