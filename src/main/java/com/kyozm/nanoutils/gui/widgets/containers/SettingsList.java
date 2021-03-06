package com.kyozm.nanoutils.gui.widgets.containers;

import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.gui.widgets.buttons.Button;
import com.kyozm.nanoutils.gui.widgets.buttons.settings.CheckButton;
import com.kyozm.nanoutils.gui.widgets.buttons.settings.ColorSetting;
import com.kyozm.nanoutils.gui.widgets.buttons.settings.FloatSlider;
import com.kyozm.nanoutils.gui.widgets.buttons.settings.IntSlider;
import com.kyozm.nanoutils.gui.widgets.buttons.settings.KeyBindButton;
import com.kyozm.nanoutils.gui.widgets.buttons.settings.NestedSettingButton;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.settings.NestedSetting;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.NanoColor;
import net.minecraft.client.settings.KeyBinding;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SettingsList extends Stacked {

    public SettingsList(Widget parent, List<Widget> widgets) {
        super(parent, widgets);
    }

    public static SettingsList fromModule(Widget parent, Class mod) {
        List<Setting> settings = ModuleManager.settings.get(mod);
        if (settings == null)
            return null;
        List<Widget> buttons = settings.stream()
                .map(SettingsList::getButtonFromSettingType)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new SettingsList(parent, buttons);
    }

    public static SettingsList fromList(Widget parent, List<Setting> settings) {
        List<Widget> buttons = settings.stream()
                .map(SettingsList::getButtonFromSettingType)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new SettingsList(parent, buttons);
    }

    private static Widget getButtonFromSettingType(Setting setting) {
        if (setting.type == null)
            return null;

        if (setting.type.equals(Boolean.class)) {
            return new Button(new CheckButton(0, 0, FontDrawer.getStringWidth(setting.name) + setting.extraW, 10, setting));
        }

        if (setting.type.equals(NestedSetting.class)) {
            return new Button(new NestedSettingButton(0, 0, FontDrawer.getStringWidth(setting.name) + setting.extraW, 10, (NestedSetting) setting));
        }

        if (setting.type.equals(Float.class)) {
            return new FloatSlider(0, 0, FontDrawer.getStringWidth(setting.name) + setting.extraW, 10, setting);
        }

        if (setting.type.equals(Integer.class)) {
            return new IntSlider(0, 0, FontDrawer.getStringWidth(setting.name) + setting.extraW, 10, setting);
        }

        if (setting.type.equals(NanoColor.class)) {
            return new Button(new ColorSetting(0, 0, FontDrawer.getStringWidth(setting.name) + setting.extraW, 10, setting));
        }

        if (setting.type.equals(KeyBinding.class)) {
            return new Button(new KeyBindButton(0, 0, FontDrawer.getStringWidth(setting.name) + setting.extraW, 10, setting));
        }

        return null;
    }

}
