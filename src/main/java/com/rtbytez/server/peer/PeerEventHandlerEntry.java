package com.rtbytez.server.peer;

import java.util.ArrayList;
import java.util.List;

public class PeerEventHandlerEntry {

    final String header;
    final PeerEventHandler peerEventHandler;
    final List<PeerEventMiddleware> middlewareList = new ArrayList<>();

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

    public void addMiddleware(PeerEventMiddleware middleware) {
        middlewareList.add(middleware);
    }

    public List<PeerEventMiddleware> getMiddleware() {
        return middlewareList;
    }
}
