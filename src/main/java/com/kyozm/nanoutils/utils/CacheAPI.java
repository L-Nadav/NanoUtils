package com.kyozm.nanoutils.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraftforge.fml.common.Loader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CacheAPI {


    private static final File clientDir = new File(Loader.instance().getConfigDir(), File.separator + "NanoUtils");
    private static final File cacheFile = new File(clientDir, "servers.cache");
    private static MultiLevelCache cache = new MultiLevelCache(cacheFile);

    public static <T, K> void setMultiLevelCache(String identifier, T key, K val) {
        cache.createOrOpen(identifier).put(key, val);
    }

    public static <T, K> Optional<K> getMultiLevelCache(String identifier, T key) {
        K cached = (K) cache.createOrOpen(identifier).get(key);
        return Optional.ofNullable(cached);
    }

    public static void saveToFile() {
        cache.commitCacheToFile();
    }

    static class MultiLevelCache implements Serializable {
        private ConcurrentMap<String, ConcurrentMap> data;
        private final File f;

        public MultiLevelCache(File f) {
            this.f = f;
            this.data = new ConcurrentHashMap<>();
            try {
                if (!f.exists()) { f.getParentFile().mkdirs(); f.createNewFile();} else {
                    try (FileReader reader = new FileReader(f)) {
                        Type stringStringMap = new TypeToken<ConcurrentMap<String, ConcurrentMap>>(){}.getType();
                        data = new Gson().fromJson(reader, stringStringMap);
                    } catch (Exception e) {
                        System.out.println("Couldn't load cache! it might be corrupted!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Couldn't create Cache File: " + e.getMessage());
            }
        }

        public void commitCacheToFile() {
            try {
                if (!f.exists()) { f.getParentFile().mkdirs(); f.createNewFile();}
                BufferedWriter out = new BufferedWriter(new FileWriter(f));
                out.write(new Gson().toJsonTree(data).toString());
                out.close();
            } catch (Exception e) {
                System.out.println("Couldn't save cache to file! " + e.getMessage());
            }
        }

        public <K, V> ConcurrentMap<K, V> createOrOpen(String identifier) {
            if (!data.containsKey(identifier))
                data.put(identifier, new ConcurrentHashMap<K, V>());
            return data.get(identifier);
        }
    }
}
