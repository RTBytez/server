package com.rtbytez.common.comms.packets.generic.error;

import com.rtbytez.common.comms.packets.RTPacketError;

public class RTPErrorInvalidArguments extends RTPacketError {

    public RTPErrorInvalidArguments(String header) {
        super(header);
        setShortCode("FileErrorInvalidArguments");
        setMessage("Invalid arguments were given in an attempt to perform this action");
    }
}
