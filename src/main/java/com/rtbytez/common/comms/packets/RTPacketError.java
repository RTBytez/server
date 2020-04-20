package com.rtbytez.common.comms.packets;

public abstract class RTPacketError extends RTPacket {

    public RTPacketError(String header) {
        super(header);
        setShortCode("GenericError");
        setError(true);
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
    }

}
