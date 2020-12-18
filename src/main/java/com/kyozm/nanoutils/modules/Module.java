package com.kyozm.nanoutils.modules;

import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Module {

    protected static final Minecraft mc = Minecraft.getMinecraft();

    public boolean enabled = false;
    public String name;
    public ModuleCategory category;
    public int bind;
    public String desc = "Module";
    public String minVersion = "0.3";
    public String moduleVersion = "0.1";
    public Map<String, Widget> saveablePositions = new HashMap<>();

    public void registerSetting(Class mod, Setting s) {
        ModuleManager.registerSetting(mod, s);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void disable() {
        enabled = false;
        onDisable();
    }

    public void enable() {
        enabled = true;
        onEnable();
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled)
            onEnable();
        else
            onDisable();
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onTick() {

    }

    public void onRender() {
        if (!enabled)
            return;
    }


    public void onKeyInput() {

    }
}
