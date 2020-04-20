package com.rtbytez.server.common.comms.packets.room.broadcasts;

import com.rtbytez.server.common.comms.packets.RTPacket;

public class RTPFileRename extends RTPacket {
    public RTPFileRename(String header) {
        super(header);
        setShortCode("FileRename");

    }
}
