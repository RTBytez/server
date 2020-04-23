package com.rtbytez.common.comms.packets.room.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPRoomRoleChange extends RTPacket {

    private final String roomId;
    private final String peerId;
    private final String peerUsername;
    private final String role;

    public RTPRoomRoleChange(String header, String roomId, String peerId, String peerUsername, String role) {
        super(header);
        setShortCode("RoomRoleChange");
        this.roomId = roomId;
        this.peerId = peerId;
        this.peerUsername = peerUsername;
        this.role = role;
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

    public String getRole() {
        return role;
    }
}
