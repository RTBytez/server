package com.rtbytez.server.events.middleware;

import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorInvalidPacket;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventMiddleware;

public class MPacketValidator extends PeerEventMiddleware {
    @Override
    public int exec(Peer peer, RTPacket packet) {
        if (!packet.isValid()) {
            peer.emit(new RTPErrorInvalidPacket(packet.getHeader()));
            return 1;
        }
        return 0;
    }
}
