package com.kyozm.nanoutils.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Optional;

import static org.lwjgl.opengl.GL11.glDepthRange;

public class MapUtils {

    private static final int NORMAL_MAP_DIMS = 64;
    public static HashMap<String, MapCache.FastMapCache> fastCache = new HashMap<>();

    public static void sendFastCacheToMemoryCache() {
        fastCache.forEach((name, c) -> {
            String serverIP = name.split("_map")[0];
            CacheAPI.setMultiLevelCache(serverIP + "_maps", "map" + name.split("_map")[1], new MapCache(c.stackName, c.colors));
        });
    }

    public static Optional<MapCache.FastMapCache> getFastCache(String name) {
        String ip = NetworkUtils.getCurrentServerIp();
        if (StringUtils.isEmpty(ip)) return Optional.empty();
        return Optional.ofNullable(fastCache.get(ip + "_" + name));
    }

    public static void cache(String mapId, MapCache.FastMapCache cache) {
        String ip = NetworkUtils.getCurrentServerIp();
        if (StringUtils.isEmpty(ip)) return;
        fastCache.put(ip + "_" + mapId, cache);
    }

    public static MapData mapDataFromStack(ItemStack stack) {
        return ((ItemMap) stack.getItem()).getMapData(stack, Minecraft.getMinecraft().world);
    }

    public static boolean renderMapFromStack(ItemStack map, int x, int y, int width, int height) {
        return renderMapFromStack(map, x, y, width, height, true);
    }

    public static boolean renderMapFromStack(ItemStack map, int x, int y, float scale) {
        int height = (int) scale * NORMAL_MAP_DIMS;
        int width = (int) scale * NORMAL_MAP_DIMS;
        return renderMapFromStack(map, x, y, width, height, true);
    }

    public static boolean renderMapFromStack(ItemStack map, int x, int y, float scale, boolean useCache) {
        int height = (int) (scale * NORMAL_MAP_DIMS);
        int width = (int) (scale * NORMAL_MAP_DIMS);
        return renderMapFromStack(map, x, y, width, height, useCache);
    }

    public static boolean renderMapFromStack(ItemStack map, int x, int y, int width, int height, boolean useCache) {
        float scaleX = (float) width / (float) NORMAL_MAP_DIMS;
        float scaleY = (float) height / (float) NORMAL_MAP_DIMS;

        MapData mapData = null; //16384
        MapData stackData = mapDataFromStack(map);
        if (useCache) {
            String mapId = "map_" + map.getMetadata();
            if (stackData != null) {
                mapData = stackData;
                MapCache.FastMapCache tmp = new MapCache.FastMapCache(map.getDisplayName(), mapData.colors);
                if (fastCache.containsKey(mapId))  {
                    if (!fastCache.get(mapId).equals(tmp))
                        cache(mapId, tmp);
                } else {
                    cache(mapId, tmp);
                }
            } else {
                Optional<MapCache.FastMapCache> fastBoi = getFastCache(mapId);
                if (fastBoi.isPresent()) { // Try Getting from fast cache
                    mapData = new MapData(mapId);
                    mapData.colors = fastBoi.get().colors;
                    Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().updateMapTexture(mapData);
                    Minecraft.getMinecraft().world.setData(mapId, mapData);
                } else {
                    MapCache storedCache = tryMapCache(mapId);
                    if (storedCache != null) { // if not in fast cache try getting from cache file / memcache
                        mapData = new MapData(mapId);
                        mapData.colors = MapCache.decodeColorData(storedCache.basedColors);
                        cache(mapData.mapName, new MapCache.FastMapCache(map.getDisplayName(), mapData.colors)); // add to fast storage
                        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().updateMapTexture(mapData);
                    }
                }
            }
        } else {
            mapData = mapDataFromStack(map);
        }

        if (mapData == null) return false;

        GL11.glPushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.color(1f, 1f, 1f, 1f); // undo any weird colors
            GlStateManager.translate(x, y, 0.0); // set pos
            GlStateManager.scale(scaleX / 2, scaleY / 2, 0.0); // scale
            GlStateManager.disableDepth();
            glDepthRange(0, 0.01);
            Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().renderMap(mapData, true);
            glDepthRange(0, 1.0);
        GL11.glPopMatrix();

        return true;
    }


    private static MapCache getValueOrCache(ItemStack map) {
        MapData stackMapData = mapDataFromStack(map);
        if (stackMapData != null) return createAndCache(mapDataFromStack(map), map.getDisplayName());
        String mapName = "map_" + map.getMetadata();
        return tryMapCache(mapName);
    }

    private static MapCache createAndCache(MapData mapData, String name) {
        String serverIP = NetworkUtils.getCurrentServerIp();
        MapCache cache = new MapCache(MapCache.encodeColorData(mapData.colors), name);
        if (StringUtils.isEmpty(serverIP)) return cache;
        CacheAPI.setMultiLevelCache(serverIP + "_maps", mapData.mapName, cache);
        return cache;
    }

    public static MapCache tryMapCache(String mapName) {
        String serverIP = NetworkUtils.getCurrentServerIp();
        if (StringUtils.isEmpty(serverIP)) return null;
        Optional<LinkedTreeMap> cached = CacheAPI.getMultiLevelCache(serverIP + "_maps", mapName);
        if (cached.isPresent()) {
            MapCache c = new Gson().fromJson(new Gson().toJson(cached.get()), MapCache.class);
            c.isFromCache = true;
            return c;
        }
        return null;
    }

    public static Image mapToImage(byte[] colors, float scale) {
        BufferedImage buffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 16384; ++i) {
            int j = colors[i] & 255;
            if (j / 4 == 0)
                buffer.setRGB(i % 128, i / 128, (i + i / 128 & 1) * 8 + 16 << 24);
            else
                buffer.setRGB(i % 128, i / 128, MapColor.COLORS[j / 4].getMapColor(j & 3));
        }
        return buffer.getScaledInstance((int) (128 * scale), (int) (128 * scale), Image.SCALE_FAST);
    }

}
