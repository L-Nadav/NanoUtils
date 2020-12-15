package com.kyozm.nanoutils.modules.gui;

import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleCategory;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.ChromaSync;
import com.kyozm.nanoutils.utils.NanoColor;
import org.lwjgl.input.Keyboard;
import scala.actors.threadpool.Arrays;

import java.awt.*;
import java.util.stream.Stream;

public class Theme extends Module {

    public Theme() {
        name = "Theme";
        category = ModuleCategory.GUI;
        bind = Keyboard.KEY_NONE;
        desc = "Color Themes";

        registerSetting(Theme.class, guiBG);
        registerSetting(Theme.class, topbarBG);
        registerSetting(Theme.class, submenuBG);
        registerSetting(Theme.class, buttonDisabledBG);
        registerSetting(Theme.class, buttonHoverBG);
        registerSetting(Theme.class, buttonEnabledBG);
        registerSetting(Theme.class, buttonDisabledFG);
        registerSetting(Theme.class, buttonEnabledFG);
        registerSetting(Theme.class, topBarButtonHover);
        registerSetting(Theme.class, topBarButtonEnabled);
        registerSetting(Theme.class, topBarButtonFG);
        registerSetting(Theme.class, nestedSettingOpenMenu);
        registerSetting(Theme.class, keybindsOnControls);
        registerSetting(Theme.class, drawSliderBorder);

        ChromaSync.updateables.add(guiBG);
        ChromaSync.updateables.add(topbarBG);
        ChromaSync.updateables.add(submenuBG);
        ChromaSync.updateables.add(buttonDisabledBG);
        ChromaSync.updateables.add(buttonHoverBG);
        ChromaSync.updateables.add(buttonEnabledBG);
        ChromaSync.updateables.add(buttonDisabledFG);
        ChromaSync.updateables.add(buttonEnabledFG);
        ChromaSync.updateables.add(topBarButtonHover);
        ChromaSync.updateables.add(topBarButtonEnabled);
        ChromaSync.updateables.add(topBarButtonFG);
        ChromaSync.updateables.add(nestedSettingOpenMenu);
    }

    public static Setting<NanoColor> topbarBG = new Setting<NanoColor>()
            .setName("TopBar BG")
            .setConfigName("ThemeTopBarBG")
            .setDefaultVal(new NanoColor(0x000000).withAlpha(0x88))
            .withType(NanoColor.class);

    public static Setting<NanoColor> guiBG = new Setting<NanoColor>()
            .setName("GUI BG")
            .setConfigName("ThemeGuiBG")
            .setDefaultVal(new NanoColor(0x000000).withAlpha(0x8E))
            .withType(NanoColor.class);

    public static Setting<NanoColor> submenuBG = new Setting<NanoColor>()
            .setName("Submenu BG")
            .setConfigName("ThemeSubmenuBG")
            .setDefaultVal(NanoColor.fromColor(Color.BLACK))
            .withType(NanoColor.class);

    public static Setting<NanoColor> buttonEnabledFG = new Setting<NanoColor>()
            .setName("Button Enabled FG")
            .setConfigName("ThemeButtonEnabledFG")
            .setDefaultVal(NanoColor.fromColor(Color.WHITE))
            .withType(NanoColor.class);

    public static Setting<NanoColor> buttonDisabledFG = new Setting<NanoColor>()
            .setName("Button Disabled FG")
            .setConfigName("ThemeButtonDisabledFG")
            .setDefaultVal(new NanoColor(0x969696))
            .withType(NanoColor.class);

    public static Setting<NanoColor> buttonDisabledBG = new Setting<NanoColor>()
            .setName("Button Disabled BG")
            .setConfigName("ThemeButtonDisabledBG")
            .setDefaultVal(new NanoColor(0x282828).withAlpha(0xAA))
            .withType(NanoColor.class);

    public static Setting<NanoColor> buttonHoverBG = new Setting<NanoColor>()
            .setName("Button Hover BG")
            .setConfigName("ThemeButtonHoverBG")
            .setDefaultVal(new NanoColor(0x606060).withAlpha(0xff))
            .withType(NanoColor.class);

    public static Setting<NanoColor> buttonEnabledBG = new Setting<NanoColor>()
            .setName("Button Enabled BG")
            .setConfigName("ThemeButtonEnabledBG")
            .setExtraWidth(15)
            .setDefaultVal(new NanoColor(0x737373).withAlpha(0xAA))
            .withType(NanoColor.class);

    public static Setting<NanoColor> topBarButtonFG = new Setting<NanoColor>()
            .setName("Top Bar FG")
            .setConfigName("ThemeTopBarFG")
            .setExtraWidth(15)
            .setDefaultVal(new NanoColor(0xFFFFFF).withAlpha(0xFF))
            .withType(NanoColor.class);

    public static Setting<NanoColor> topBarButtonHover = new Setting<NanoColor>()
            .setName("Top Bar Hover BG")
            .setConfigName("ThemeTopBarHover")
            .setExtraWidth(15)
            .setDefaultVal(new NanoColor(0x565656).withAlpha(0xAA))
            .withType(NanoColor.class);

    public static Setting<NanoColor> topBarButtonEnabled = new Setting<NanoColor>()
            .setName("Top Bar Enabled BG")
            .setConfigName("ThemeTobBarEnabled")
            .setExtraWidth(15)
            .setDefaultVal(new NanoColor(0x000000).withAlpha(0xFF))
            .withType(NanoColor.class);

    public static Setting<NanoColor> nestedSettingOpenMenu = new Setting<NanoColor>()
            .setName("Nested Setting Open")
            .setConfigName("ThemeNestedOpen")
            .setExtraWidth(15)
            .setDefaultVal(new NanoColor(0x606060).withAlpha(0xff))
            .withType(NanoColor.class);

    public static Setting<Boolean> drawSliderBorder = new Setting<Boolean>()
            .setName("Draw Slider Border")
            .setConfigName("ThemeSliderBorder")
            .setDefaultVal(true)
            .withType(Boolean.class);

    public static Setting<Boolean> keybindsOnControls = new Setting<Boolean>()
            .setName("Keybinds on Controls GUI")
            .setConfigName("ThemeKeybindsControls")
            .setDefaultVal(false)
            .withType(Boolean.class);

    @Override
    public void onEnable() {
        disable();
        topbarBG.setVal(topbarBG.getDefault());
        guiBG.setVal(guiBG.getDefault());
        submenuBG.setVal(submenuBG.getDefault());
        buttonDisabledBG.setVal(buttonDisabledBG.getDefault());
        buttonDisabledFG.setVal(buttonDisabledFG.getDefault());
        buttonEnabledBG.setVal(buttonEnabledBG.getDefault());
        buttonEnabledFG.setVal(buttonEnabledFG.getDefault());
        buttonHoverBG.setVal(buttonHoverBG.getDefault());
        topBarButtonEnabled.setVal(topBarButtonEnabled.getDefault());
        topBarButtonFG.setVal(topBarButtonFG.getDefault());
        topBarButtonHover.setVal(topBarButtonHover.getDefault());
        nestedSettingOpenMenu.setVal(nestedSettingOpenMenu.getDefault());
    }
}
