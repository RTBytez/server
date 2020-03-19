package com.rtbytez.server.events.handlers;

import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventData;
import com.rtbytez.server.peer.PeerEventHandler;

public class EchoEvent extends PeerEventHandler {

    public EchoEvent() {
        super();
    }

    @Override
    public void exec(String header, Peer peer, PeerEventData data) {
        peer.emit(header, data.getAsString());
    }
}
