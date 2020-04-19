package com.rtbytez.server.common.comms.packets.room.ok;

import com.rtbytez.server.common.comms.packets.RTPacket;

public class RTPRoomJoin extends RTPacket {

    public RTPRoomJoin(String header, String roomId, String peerId, String peerUsername) {
        super(header);
        setShortCode("RoomJoin");
        put("roomId", roomId);
        put("peerId", peerId);
        put("peerUsername", peerUsername);
    }

    public String getRoomId() {
        return getString("roomId");
    }

    public String getPeerId() {
        return getString("peerId");
    }

    public String getPeerUsername() {
        return getString("peerUsername");
    }
}