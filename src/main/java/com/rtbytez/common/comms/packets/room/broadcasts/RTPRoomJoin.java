package com.rtbytez.common.comms.packets.room.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPRoomJoin extends RTPacket {

    private final String roomId;
    private final String peerId;
    private final String peerUsername;

    public RTPRoomJoin(String header, String roomId, String peerId, String peerUsername) {
        super(header);
        setShortCode("RoomJoin");
        this.roomId = roomId;
        this.peerId = peerId;
        this.peerUsername = peerUsername;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPeerId() {
        return peerId;
    }

    public String getPeerUsername() {
        return peerUsername;
    }
}