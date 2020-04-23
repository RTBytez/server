package com.rtbytez.common.comms.packets.generic.ok;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPOK extends RTPacket {
    public RTPOK(String header) {
        super(header);
        setShortCode("OK");
    }
}
