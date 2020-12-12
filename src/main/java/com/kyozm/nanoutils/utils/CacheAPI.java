package com.kyozm.nanoutils.utils;

import net.minecraftforge.fml.common.Loader;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class CacheAPI {

    public static DB db;
    public static DB mem = DBMaker.memoryDB().make();
    private static final File clientDir = new File(Loader.instance().getConfigDir(), File.separator + "NanoUtils");

    private static void openDB() {
        if (!clientDir.exists()) clientDir.mkdirs();
        db = DBMaker.fileDB(new File(clientDir, "Cache.db")).make();
    }

    public static <T, K> void setMultiLevelCache(String identifier, T key, K val) {
        ConcurrentMap mapMem = mem.hashMap(identifier).createOrOpen();
        if (mapMem.containsKey(key)) return;
        openDB();
        ConcurrentMap map = db.hashMap(identifier).createOrOpen();
        map.put(key, val);
        mapMem.put(key, val);
        db.close();
    }

    public static <T, K> Optional<K> getMultiLevelCache(String identifier, T key) {
        ConcurrentMap mapMem = mem.hashMap(identifier).createOrOpen();
        if (mapMem.containsKey(key))
            return Optional.of((K) mapMem.get(key));
        else {
            openDB();
            ConcurrentMap map = db.hashMap(identifier).createOrOpen();
            K value = (K) map.get(key);
            db.close();
            if (value != null)
                setMultiLevelCache(identifier, key, value);
            return Optional.ofNullable(value);
        }
    }

}
