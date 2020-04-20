package com.rtbytez.common.comms.packets.generic.error;

import com.rtbytez.common.comms.packets.RTPacketError;

public class RTPErrorGeneric extends RTPacketError {

    public RTPErrorGeneric(String header, String message) {
        super(header);
        setShortCode("ErrorGeneric");
        setError(true);
        setMessage(message);
    }

    public RTPErrorGeneric(String header) {
        super(header);
        setShortCode("GenericError");
        setError(true);
    }
}
