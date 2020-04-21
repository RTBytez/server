package com.rtbytez.common.comms.packets.file.error;

import com.rtbytez.common.comms.packets.RTPacketError;

public class RTPFileErrorLineAlreadyExists extends RTPacketError {

    public RTPFileErrorLineAlreadyExists(String header) {
        super(header, "A Line with that ID already exists");
        setShortCode("FileErrorLineAlreadyExists");
    }
}