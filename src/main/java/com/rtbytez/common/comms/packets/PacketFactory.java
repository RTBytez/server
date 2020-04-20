package com.rtbytez.common.comms.packets;

import com.rtbytez.common.util.RTJSON;
import org.reflections.Reflections;

import java.util.Set;

public class PacketFactory {
    private static Set<Class<? extends RTPacket>> allClasses;

    public static void scanAllPackets() {
        Reflections reflections = new Reflections("com.rtbytez.common.comms.packets");
        allClasses = reflections.getSubTypesOf(RTPacket.class);
    }

    public static RTPacket createPacket(String header, RTJSON rtjson) {
        scanAllPackets();
        for (Class<? extends RTPacket> p : allClasses) {
            if (rtjson.getString("shortCode").equals(p.getSimpleName())) {
                try {
                    return p.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
