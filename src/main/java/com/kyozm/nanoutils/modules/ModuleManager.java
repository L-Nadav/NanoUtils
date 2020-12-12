package com.kyozm.nanoutils.modules;

import com.kyozm.nanoutils.modules.gui.NanoGuiModule;
import com.kyozm.nanoutils.modules.render.Earthquake;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.settings.NestedSetting;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.ChromaSync;
import com.kyozm.nanoutils.utils.Config;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ModuleManager {
    public static ArrayList<Module> active = new ArrayList<>();
    public static ArrayList<Module> registered = new ArrayList<>();
    public static Map<Class, List<Setting>> settings = new HashMap<>();

    public static void register() {
        // RENDER
        registered.add(new MapPreview());
        registered.add(new Earthquake()); //For the LOLZ haha

        // GUI
        registered.add(new NanoGuiModule());

        Config.loadConfig();
    }

    public static boolean isActive(Class module) {
        if (Module.class.isAssignableFrom(module)) {
            Optional<Module> mod = registered.stream().filter(s -> s.getClass().equals(module)).findAny();
            return mod.isPresent() && mod.get().isEnabled();
        }

        return false;
    }

    public static Optional<Module> getModule(Class module) {
        if (Module.class.isAssignableFrom(module)) {
            return registered.stream().filter(s -> s.getClass().equals(module)).findAny();
        }

        return Optional.empty();
    }

    public static void clientTick() {
        active.forEach(Module::onTick);
    }

    public static void enable(Class module) {
        if (Module.class.isAssignableFrom(module)) {
            Optional<Module> mod = registered.stream().filter(s -> s.getClass().equals(module)).findAny();
            mod.ifPresent(m -> {
                if (!active.contains(m)) {
                    m.enable();
                    active.add(m);
                }
            });
        }
    }

    public static void disable(Class module) {
        if (Module.class.isAssignableFrom(module)) {
            Optional<Module> mod = registered.stream().filter(s -> s.getClass().equals(module)).findAny();
            mod.ifPresent(m -> {
                m.disable();
                active.remove(m);
            });
        }
    }

    public static void onRender() {
        ChromaSync.step();
        registered.forEach(Module::onRender);
    }

    public static void keyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if(Keyboard.getEventKey() == Keyboard.KEY_NONE) return;
            registered.stream().filter(m -> m.bind == Keyboard.getEventKey()).forEach(Module::toggle);
        }
    }

    public static void registerSetting(Class mod, Setting s) {
        if (!settings.containsKey(mod))
            settings.put(mod, new ArrayList<>());
        settings.get(mod).add(s);
    }

    public static <T> void setSettingByConfigName(String configName, T val) {
        for (Map.Entry<Class, List<Setting>> e : settings.entrySet()) {
            for (Setting s : e.getValue()) {
                if (NestedSetting.class.isAssignableFrom(s.getClass())) {
                    for (Setting sn : ((NestedSetting) s).internal) {
                        if (sn.configName.equals(configName)) sn.setVal(val);
                    }
                } else {
                    if (s.configName.equals(configName)) s.setVal(val);
                }
            }
        }
    }

    public static List<Module> findByCategory(ModuleCategory category) {
        return registered.stream().filter(m -> m.category.equals(category)).collect(Collectors.toList());
    }
}
