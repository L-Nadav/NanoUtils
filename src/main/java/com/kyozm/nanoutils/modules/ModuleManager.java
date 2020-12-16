package com.kyozm.nanoutils.modules;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.NanoGui;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.modules.gui.NanoGuiModule;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.modules.render.Earthquake;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.modules.render.ShulkerPreview;
import com.kyozm.nanoutils.settings.NestedSetting;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.ChromaSync;
import com.kyozm.nanoutils.utils.Config;
import com.kyozm.nanoutils.utils.Keybinds;
import com.kyozm.nanoutils.utils.NanoColor;
import com.kyozm.nanoutils.utils.TickTimer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModuleManager {
    public static ArrayList<Module> active = new ArrayList<>();
    public static ArrayList<Module> registered = new ArrayList<>();
    public static Map<String, TickTimer> timers = new HashMap<>();
    public static Map<Class, List<Setting>> settings = new HashMap<>();

    public static void register() {
        // GUI
        registered.add(new Theme());
        registered.add(new NanoGuiModule());

        // RENDER
        registered.add(new MapPreview());
        registered.add(new ShulkerPreview());

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
        timers.forEach((k, timer) -> timer.step());
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
        if (Keybinds.openGUI.isPressed()) {
            getModule(NanoGuiModule.class).ifPresent(Module::toggle);
        }

        if (Keyboard.getEventKeyState()) {
            if(Keyboard.getEventKey() == Keyboard.KEY_NONE) return;
            registered.stream().filter(m -> m.bind == Keyboard.getEventKey()).forEach(Module::toggle);
        }

        registered.forEach(Module::onKeyInput);
    }

    public static void registerSetting(Class mod, Setting s) {
        if (!settings.containsKey(mod))
            settings.put(mod, new ArrayList<>());
        settings.get(mod).add(s);

        if (s.type == KeyBinding.class && Theme.keybindsOnControls.getVal()) {
            ClientRegistry.registerKeyBinding((KeyBinding) s.getVal());
        }
    }

    public static <T> void setSettingByConfigName(String configName, T val) {
        for (Map.Entry<Class, List<Setting>> e : settings.entrySet()) {
            setSettingByConfigName(e.getValue(), configName, val);
        }
    }

    public static <T> void setSettingByConfigName(List<Setting> settingsList, String configName, T val) {
        for (Setting s : settingsList) {
            if (NestedSetting.class.isAssignableFrom(s.getClass())) {
                setSettingByConfigName(((NestedSetting) s).internal, configName, val);
            }
            setValIfSame(configName, val, s);
        }
    }

    private static <T> void setValIfSame(String configName, T val, Setting sn) {
        if (sn.configName.equals(configName)) {
            if (sn.type == Float.class)
                sn.setVal(Double.class.cast(val).floatValue());
            else if (sn.type == Integer.class)
                sn.setVal(Double.class.cast(val).intValue());
            else if (sn.type == NestedSetting.class)
                sn.setVal(Boolean.class.cast(val));
            else if (sn.type == NanoColor.class)
                sn.setVal(Config.gs().fromJson(Config.gs().toJsonTree(val), NanoColor.class));
            else if (sn.type == KeyBinding.class) {
                KeyBinding newKb = Config.gs().fromJson(Config.gs().toJsonTree(val), KeyBinding.class);
                ((KeyBinding)sn.getVal()).setKeyModifierAndCode(newKb.getKeyModifier(), newKb.getKeyCode());
            }
            else
                sn.setVal(sn.type.cast(val));
        }
    }

    public static List<Module> findByCategory(ModuleCategory category) {
        return registered.stream().filter(m -> m.category.equals(category)).collect(Collectors.toList());
    }

    public static void setGuiPosition(String id, Map<String, Double> pos) {
        registered.forEach(mod -> mod.saveablePositions.entrySet().stream().filter(w -> w.getKey().equals(id)).findAny().ifPresent(w -> {
                Widget widget = w.getValue();
                widget.screenX = pos.get("x").intValue();
                widget.screenY = pos.get("y").intValue();
                NanoUtils.gui.queue.add(widget);
                mod.saveablePositions.put(id, widget);
        }));
    }

    public static boolean isTimerExpired(String tooltipClipboard) {
        if (timers.containsKey(tooltipClipboard))
            return timers.get(tooltipClipboard).isExpired;
        return true;
    }
}
