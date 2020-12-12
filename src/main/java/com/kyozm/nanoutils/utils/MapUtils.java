package com.kyozm.nanoutils.utils;

import com.kyozm.nanoutils.listeners.ModuleManagerDriver;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import java.util.Optional;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glDepthRange;

public class MapUtils {

    private static final int NORMAL_MAP_DIMS = 64;

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

        MapCache data = useCache ? getValueOrCache(map) : new MapCache(mapDataFromStack(map), map.getDisplayName());
        if (StringUtils.isEmpty(data.basedColors)) return false;

        //16384
        MapData mapData;
        if (data.isFromCache) {
            mapData = new MapData("map_" + map.getMetadata());
            mapData.colors = MapCache.decodeColorData(data.basedColors);
            Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().updateMapTexture(mapData);
        } else {
            mapData = mapDataFromStack(map);
        }

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
        Optional<MapCache> cached = CacheAPI.getMultiLevelCache(serverIP + "_maps", mapName);
        if (cached.isPresent()) {
            MapCache c = cached.get();
            c.isFromCache = true;
            return c;
        }
        return null;
    }

}
