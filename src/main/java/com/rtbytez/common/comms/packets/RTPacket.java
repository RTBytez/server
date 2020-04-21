package com.rtbytez.common.comms.packets;

import com.google.gson.annotations.Expose;

public abstract class RTPacket {

    private final String header;
    private String shortCode;
    private boolean isError;

    @Expose(serialize = false, deserialize = false)
    private boolean isValid = true;

    public RTPacket(String header) {
        this.header = header;
        setError(false);
    }

    /**
     * Convert this RTPacket into the transferable JSON string
     */
    public String toJsonString() {
        return PacketFactory.GSON.toJson(this);
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getHeader() {
        return header;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + toJsonString();
    }
}
