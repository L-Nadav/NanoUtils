package com.kyozm.nanoutils.utils;

import com.kyozm.nanoutils.NanoUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import scala.Tuple4;

public class InputUtils {

    public static boolean leftHeld = false;

    public static Tuple4<Integer, Integer, Integer, Boolean> getMouseData() {
        Minecraft mc = Minecraft.getMinecraft();

        return new Tuple4<>(
                Mouse.getEventX() / NanoUtils.sr.getScaleFactor(),
                (mc.displayHeight - Mouse.getEventY()) / NanoUtils.sr.getScaleFactor(),
                Mouse.getEventButton(),
                leftHeld);
    }

    public static boolean isKeybindHeld(KeyBinding k) {
        return Keyboard.isKeyDown(k.getKeyCode()) && k.getKeyModifier().isActive(null);
    }
}
