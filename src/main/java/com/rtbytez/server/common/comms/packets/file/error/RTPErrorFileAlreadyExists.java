package com.rtbytez.server.common.comms.packets.file.error;

import com.rtbytez.server.common.comms.packets.RTPacketError;

public class RTPErrorFileAlreadyExists extends RTPacketError {
    public RTPErrorFileAlreadyExists(String header) {
        super(header);
        setShortCode("ErrorFileAlreadyExists");
        setMessage("File already exists");
    }
}
