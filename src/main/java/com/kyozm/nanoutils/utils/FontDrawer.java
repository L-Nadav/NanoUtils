package com.kyozm.nanoutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class FontDrawer {
    public static FontRenderer mcFont = Minecraft.getMinecraft().fontRenderer;

    public static void drawString(String string, int x, int y, Color color) {
            mcFont.drawString(string, x, y, color.getRGB());
    }

    public static int getStringWidth(String string) {
        return mcFont.getStringWidth(string);
    }

    public static int getFontHeight() {
        return mcFont.FONT_HEIGHT;
    }
}
