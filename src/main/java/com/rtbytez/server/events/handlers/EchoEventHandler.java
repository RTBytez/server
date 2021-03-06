package com.rtbytez.server.events.handlers;

import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventHandler;

public class EchoEventHandler extends PeerEventHandler {

    public EchoEventHandler() {
        super();
    }

    @Override
    public void exec(Peer peer, RTPacket packet) {
        peer.emit(packet);
    }
}
