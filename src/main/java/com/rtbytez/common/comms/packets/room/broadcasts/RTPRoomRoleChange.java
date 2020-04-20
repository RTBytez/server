package com.rtbytez.common.comms.packets.room.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPRoomRoleChange extends RTPacket {
    public RTPRoomRoleChange(String header, String roomId, String peerId, String peerUsername, String role) {
        super(header);
        setShortCode("RoomRoleChange");
        put("roomId", roomId);
        put("peerId", peerId);
        put("peerUsername", peerUsername);
        put("role", role);
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

    public String getRole() {
        return getString("role");
    }
}
