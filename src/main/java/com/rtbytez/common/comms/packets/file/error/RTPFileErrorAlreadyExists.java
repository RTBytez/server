package com.rtbytez.common.comms.packets.file.error;

import com.rtbytez.common.comms.packets.RTPacketError;

public class RTPFileErrorAlreadyExists extends RTPacketError {

    public RTPFileErrorAlreadyExists(String header) {
        super(header, "File already exists");
        setShortCode("FileErrorAlreadyExists");
    }
}