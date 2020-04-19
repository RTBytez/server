package com.rtbytez.server.common.comms.packets.file.error;

import com.rtbytez.server.common.comms.packets.RTPacketError;

public class RTPErrorFileLineAlreadyExists extends RTPacketError {
    public RTPErrorFileLineAlreadyExists(String header) {
        super(header);
        setShortCode("ErrorFileLineAlreadyExists");
        setMessage("A Line with that ID already exists");
    }
}
