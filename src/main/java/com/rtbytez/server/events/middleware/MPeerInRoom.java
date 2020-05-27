package com.rtbytez.server.events.middleware;

import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventMiddleware;
import com.rtbytez.server.room.RoomManager;

public class MPeerInRoom extends PeerEventMiddleware {
    @Override
    public int exec(Peer peer, RTPacket packet) {
        if (RoomManager.getRoomOf(peer) == null) {
            return 1;
        }
        return 0;
    }
}
