package com.rtbytez.common.comms.packets.room.error;

import com.rtbytez.common.comms.packets.RTPacketError;

public class RTPRoomErrorNotInARoom extends RTPacketError {

    public RTPRoomErrorNotInARoom(String header) {
        super(header, "You are not in a room");
        setShortCode("FileErrorNotInARoom");
    }
}