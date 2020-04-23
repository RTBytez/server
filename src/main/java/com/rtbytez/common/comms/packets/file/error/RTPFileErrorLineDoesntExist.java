package com.rtbytez.common.comms.packets.file.error;

import com.rtbytez.common.comms.packets.RTPacketError;

public class RTPFileErrorLineDoesntExist extends RTPacketError {
    public RTPFileErrorLineDoesntExist(String header) {
        super(header, "No such line exists");
        setShortCode("FileErrorNoSuchLineExists");
    }
}
