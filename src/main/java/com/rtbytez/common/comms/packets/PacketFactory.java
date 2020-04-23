package com.rtbytez.common.comms.packets;

import com.google.gson.Gson;
import com.rtbytez.server.util.Console;
import org.json.JSONObject;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

import static com.rtbytez.server.util.Functions.disableLoggerFor;

public class PacketFactory {

    public static final Gson GSON = new Gson();
    private static final HashMap<String, Class<? extends RTPacket>> allRTPacketClasses = new HashMap<>();

    static {
        disableLoggerFor(Reflections.class);
        Reflections reflections = new Reflections("com.rtbytez.common.comms.packets");
        Set<Class<? extends RTPacket>> classesSet = reflections.getSubTypesOf(RTPacket.class);
        classesSet.forEach(aClass -> {
            String className = aClass.getSimpleName().replaceFirst("RTP", "");
            allRTPacketClasses.put(className, aClass);
        });
    }

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private PacketFactory() {

    }

    public static RTPacket createPacket(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Class<? extends RTPacket> clazz = allRTPacketClasses.get(jsonObject.getString("shortCode"));
            return GSON.fromJson(json, clazz);
        } catch (Exception e) {
            Console.log("PacketFactory", "Created an invalid packet for: " + json);
            RTPacket rtPacket = new RTPacket("error") {
            };
            rtPacket.setValid(false);
            return rtPacket;
        }
    }
}
