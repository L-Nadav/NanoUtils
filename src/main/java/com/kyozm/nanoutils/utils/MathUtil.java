package com.kyozm.nanoutils.utils;

public class MathUtil {

    // https://stackoverflow.com/questions/6607114/java-rounding-by-specific-step
    public static float round(float input, float step) {
        return (Math.round(input / step) * step);
    }

    public static float percent(float min, float max, float v) {
        return (v - min) / (max - min);
    }

    public static float fromPercent(float min, float max, float p) {
        return ((max - min) * p) + min;
    }

    public static float lerp(int a, int b, float f) {
        return a + f * (b - a);
    }

    public static float scale(int a, int b, int x, int y, float v) {
        return fromPercent(x, y, percent(a, b, v));
    }
}
