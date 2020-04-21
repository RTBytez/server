package com.rtbytez.common.comms.packets.generic.error;

import com.rtbytez.common.comms.packets.RTPacketError;

public class RTPErrorGeneric extends RTPacketError {

    public RTPErrorGeneric(String header, String message) {
        super(header, message);
        setShortCode("ErrorGeneric");
    }

    public RTPErrorGeneric(String header) {
        super(header, "No further information");
        setShortCode("ErrorGeneric");
    }
}
