package com.kyozm.nanoutils.settings;

import com.kyozm.nanoutils.modules.gui.Theme;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;

public class NestedSetting extends Setting<Boolean> {
    public List<Setting> internal = new ArrayList<>();

    public void register(Setting s) {
        internal.add(s);
        if (s.type == KeyBinding.class && Theme.keybindsOnControls.getVal()) {
            ClientRegistry.registerKeyBinding((KeyBinding) s.getVal());
        }
    }

    @Override
    public NestedSetting setDefaultVal(Boolean defaultVal) {
        this.setVal(defaultVal);
        return this;
    }

    @Override
    public NestedSetting setConfigName(String configName) {
        this.configName = configName;
        return this;
    }

    @Override
    public NestedSetting setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public NestedSetting withType(Class type) {
        this.type = type;
        return this;
    }

    @Override
    public NestedSetting setExtraWidth(int extraW) {
        this.extraW = extraW;
        return this;
    }
}
