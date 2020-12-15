package com.kyozm.nanoutils.settings;

public class Setting<T> {

    public String name;
    public String configName;
    private T val = null;
    private T defaultVal = null;
    public T minVal = null;
    public T maxVal = null;
    public T step = null;
    public int extraW = 0;

    public Class<T> type;

    public T getVal() {
        return val;
    }

    public void setVal(T v) {
        val = v;
    }

    public Setting<T> setName(String name) {
        this.name = name;
        return this;
    }

    public Setting<T> setConfigName(String configName) {
        this.configName = configName;
        return this;
    }

    public Setting<T> setDefaultVal(T defaultVal) {
        this.defaultVal = defaultVal;
        this.val = defaultVal;
        return this;
    }

    public T getDefault() {
        return defaultVal;
    }

    public Setting<T> setExtraWidth(int w) {
        extraW = w;
        return this;
    }

    public Setting<T> setMinVal(T minVal) {
        this.minVal = minVal;
        return this;
    }

    public Setting<T> setMaxVal(T maxVal) {
        this.maxVal = maxVal;
        return this;
    }

    public Setting<T> withStep(T step) {
        this.step = step;
        return this;
    }

    public Setting<T> withType(Class<T> type) {
        this.type = type;
        return this;
    }
}
