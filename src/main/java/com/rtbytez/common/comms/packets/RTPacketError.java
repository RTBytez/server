package com.rtbytez.common.comms.packets;

public abstract class RTPacketError extends RTPacket {

    private final String message;

    public RTPacketError(String header, String message) {
        super(header);
        this.message = message;
        setShortCode("GenericError");
        setError(true);
    }

}
