package com.kyozm.nanoutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class NetworkUtils {

    public static String getCurrentServerIp() {
        ServerData server = Minecraft.getMinecraft().getCurrentServerData();
        if (server == null) return null;
        return server.serverIP;
    }


}
