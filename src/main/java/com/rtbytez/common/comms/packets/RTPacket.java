package com.rtbytez.common.comms.packets;

import com.rtbytez.common.util.RTJSON;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class RTPacket {

    private RTJSON raw = new RTJSON();
    private String header = "";


    public RTPacket(String header) {
        this.header = header;
        setError(false);
    }

    public RTPacket() {

    }

    public void setRTJSON(RTJSON rtjson) {
        this.raw = rtjson;
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

    public boolean isValid() {
        Class thisClass = this.getClass();
        for (Method method : thisClass.getMethods()) {
            if (method.getDeclaringClass().equals(thisClass)) {
                try {
                    method.invoke(this);
                } catch (IllegalAccessException e) {
                    return false;
                } catch (InvocationTargetException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
