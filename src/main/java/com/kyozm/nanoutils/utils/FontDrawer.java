package com.kyozm.nanoutils.utils;

import external.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class FontDrawer {
    public static CFontRenderer smooth = new CFontRenderer(new Font("Tahoma", 0, 14), true, true);
    public static FontRenderer mcFont = Minecraft.getMinecraft().fontRenderer;
    public static boolean useSmooth = true;

    public static void drawString(String string, int x, int y, Color color) {
        if (useSmooth) {
            smooth.drawString(string, x, y, color.getRGB());
        } else {
            mcFont.drawString(string, x, y, color.getRGB());
        }
    }

    public static int getStringWidth(String string) {
        return useSmooth ? smooth.getStringWidth(string) : mcFont.getStringWidth(string);
    }

    public static int getFontHeight() {
        return useSmooth ? smooth.getStringHeight("A"): mcFont.FONT_HEIGHT;
    }
}
