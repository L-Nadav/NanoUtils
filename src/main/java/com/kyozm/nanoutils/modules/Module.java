package com.kyozm.nanoutils.modules;

import com.kyozm.nanoutils.settings.Setting;
import net.minecraft.client.Minecraft;

public abstract class Module {

    protected static final Minecraft mc = Minecraft.getMinecraft();

    public boolean enabled = false;
    public String name;
    public ModuleCategory category;
    public int bind;
    public String desc = "Module";

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


}
