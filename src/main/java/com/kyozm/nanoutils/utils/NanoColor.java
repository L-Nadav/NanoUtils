package com.kyozm.nanoutils.utils;

import com.kyozm.nanoutils.gui.widgets.ColorPicker;
import org.jline.builtins.Nano;

import java.awt.*;

public class NanoColor extends java.awt.Color {
    public boolean isChroma = false;

    public NanoColor(int r, int g, int b) {
        super(r, g, b);
    }

    public NanoColor(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public NanoColor(int rgb) {
        super(rgb);
    }

    public NanoColor withAlpha(int a) {
        NanoColor instance = new NanoColor(getRed(), getGreen(), getBlue(), a);
        instance.isChroma = this.isChroma;
        return instance;
    }

    public static NanoColor fromColor(Color c) {
        return new NanoColor(c.getRGB());
    }

    public String getStringWithAlpha() {
        return String.format("#%02x%02x%02x", getRed(), getGreen(), getBlue());
    }

    public NanoColor applyChroma() {
        float hue = ColorPicker.getHue(ChromaSync.current);
        float sat = ColorPicker.getSaturation(this);
        float bri =  ColorPicker.getBrightness(this);
        NanoColor instance = fromColor(NanoColor.getHSBColor(hue, sat, bri));
        instance.isChroma = this.isChroma;
        return instance.withAlpha(getAlpha());
    }
}
