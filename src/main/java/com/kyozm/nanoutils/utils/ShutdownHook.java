package com.kyozm.nanoutils.utils;

public class ShutdownHook extends Thread {
    @Override
    public void run() {
        Config.saveConfig();
        MapUtils.sendFastCacheToMemoryCache();
        CacheAPI.saveToFile();
    }
}
