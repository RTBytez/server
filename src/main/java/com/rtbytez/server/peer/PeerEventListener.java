package com.rtbytez.server.peer;

import io.socket.socketio.server.SocketIoSocket;

import java.util.HashMap;
import java.util.Map;

/**
 * Internal Use Only - Handles events that are sent across the tunnel to written receivers
 */
class PeerEventListener implements SocketIoSocket.AllEventListener {

    //TODO: Document later
    private final Peer peer;
    private HashMap<String, PeerEventHandler> handlers = new HashMap<>();

    PeerEventListener(Peer peer) {
        this.peer = peer;
    }

    public void addEventHandler(String header, PeerEventHandler handler) {
        handlers.put(header, handler);
    }

    //TODO: Write later
    public void removeEventHandler(String header) {

    }

    //TODO: Write later
    public void removeAllEventHandlers(String header) {

    }

    /**
     * Remove absolutely every event handler from this peer
     */
    public void removeAllEventHandlers() {
        handlers.clear();
    }

    @Override
    public void event(String eventName, Object... args) {
        for (Map.Entry<String, PeerEventHandler> entry : handlers.entrySet()) {
            if (entry.getKey().equals(eventName)) {
                entry.getValue().exec(eventName, new PeerEventData(args));
            }
        }
    }
}
