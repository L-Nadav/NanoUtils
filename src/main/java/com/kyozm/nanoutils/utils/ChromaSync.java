package com.kyozm.nanoutils.utils;

import java.awt.*;

public class ChromaSync {
    public static Color current = new Color(1, 1, 1);

    public static void step() {
        current = new Color(Color.HSBtoRGB((System.currentTimeMillis() % (360 * 48)) / (360f * 48), 1, 1));
    }
}
