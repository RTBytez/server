package com.rtbytez.server.common.comms.packets;

import com.rtbytez.server.common.comms.packets.file.FileRequestNew;
import com.rtbytez.server.common.util.RTJSON;

import java.lang.reflect.Method;

public abstract class RTPacket {

    private final RTJSON raw = new RTJSON(); // TODO: Set back to final
    private String header = "";

    public RTPacket(String header) {
        this.header = header;
        setError(false);
    }

    public static void main(String[] args) {
        RTJSON rtjson = new RTJSON("{'shortCode': 'FileRequestNew', 'filePath': '/var/log/foo', 'lineNumber': 5}");
        FileRequestNew packet = new FileRequestNew(null, null, 0);

        packet.isValid();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getShortCode() {
        return raw.getString("shortCode", "");
    }

    public void setShortCode(String shortCode) {
        raw.put("shortCode", shortCode);
    }

    public boolean isError() {
        return raw.getBoolean("isError", false);
    }

    public void setError(boolean error) {
        raw.put("isError", error);
    }

    public String getRaw() {
        return raw.toString();
    }

    public void put(String key, String value) {
        raw.put(key, value);
    }

    public void put(String key, int value) {
        raw.put(key, value);
    }

    public void put(String key, boolean value) {
        raw.put(key, value);
    }

    public String getString(String key) {
        return raw.getString(key, "");
    }

    public int getInt(String key) {
        return raw.getInt(key);
    }

    public boolean getBoolean(String key) {
        return raw.getBoolean(key, false);
    }

    public void isValid() {
        Class thisClass = this.getClass();
        for (Method method : thisClass.getMethods()) {
            System.out.println(method);
        }
    }
}
