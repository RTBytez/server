package com.rtbytez.server.common.comms.packets.room.error;

import com.rtbytez.server.common.comms.packets.RTPacketError;

public class RTPRoomErrorNotInARoom extends RTPacketError {

    public RTPRoomErrorNotInARoom(String header) {
        super(header);
        setShortCode("FileErrorNotInARoom");
        setMessage("You are not in a room");
    }
}