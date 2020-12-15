package com.kyozm.nanoutils.utils;

import com.kyozm.nanoutils.settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class ChromaSync {
    public static NanoColor current = new NanoColor(1, 1, 1);
    public static List<Setting<NanoColor>> updateables = new ArrayList<>();

    public static void step() {
        current = new NanoColor(NanoColor.HSBtoRGB((System.currentTimeMillis() % (360 * 48)) / (360f * 48), 1, 1));
        updateables.stream().filter(s -> s.getVal().isChroma).forEach(s -> s.setVal(s.getVal().applyChroma()));
    }
}
