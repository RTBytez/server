package com.rtbytez.server.common.comms.packets.file.error;

import com.rtbytez.server.common.comms.packets.RTPacketError;

public class RTPErrorFileDoesntExist extends RTPacketError {
    public RTPErrorFileDoesntExist(String header) {
        super(header);
        setShortCode("ErrorFileDoesntExist");
        setMessage("The file doesn't exist");
    }
}
