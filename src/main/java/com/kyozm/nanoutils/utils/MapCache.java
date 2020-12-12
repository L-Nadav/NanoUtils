package com.kyozm.nanoutils.utils;

import net.minecraft.world.storage.MapData;

import java.io.Serializable;
import java.util.Base64;

public class MapCache implements Serializable {
    String basedColors;
    String stackName;
    public boolean isFromCache = false;

    public MapCache(String mapData, String stackName) {
        this.basedColors = mapData;
        this.stackName = stackName;
    }

    public MapCache(MapData mapData, String stackName) {
        this.basedColors = encodeColorData(mapData.colors);
        this.stackName = stackName;
    }

    public String getBasedColors() {
        return basedColors;
    }

    public MapCache setBasedColors(String basedColors) {
        this.basedColors = basedColors;
        return this;
    }

    public String getStackName() {
        return stackName;
    }

    public MapCache setStackName(String stackName) {
        this.stackName = stackName;
        return this;
    }

    public static String encodeColorData(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decodeColorData(String encoded) {
        return Base64.getDecoder().decode(encoded.getBytes());
    }
}
