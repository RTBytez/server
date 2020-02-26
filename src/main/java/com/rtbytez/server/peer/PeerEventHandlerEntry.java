package com.rtbytez.server.peer;

public class PeerEventHandlerEntry {

    String header;
    PeerEventHandler peerEventHandler;

    public PeerEventHandlerEntry(String header, PeerEventHandler handler) {
        this.header = header;
        this.peerEventHandler = handler;
    }

    public String getHeader() {
        return header;
    }

    public PeerEventHandler getPeerEventHandler() {
        return peerEventHandler;
    }
}
