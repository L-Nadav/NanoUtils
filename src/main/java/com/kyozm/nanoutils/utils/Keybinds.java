package com.kyozm.nanoutils.utils;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {

    public static KeyBinding openGUI;

    public static void register()
    {
        openGUI = new KeyBinding("Open GUI", Keyboard.KEY_SEMICOLON, "NanoUtils");
        ClientRegistry.registerKeyBinding(openGUI);
    }
}
