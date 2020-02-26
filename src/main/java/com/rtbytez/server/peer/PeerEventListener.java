package com.rtbytez.server.peer;

import io.socket.socketio.server.SocketIoSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal Use Only - Handles events that are sent across the tunnel to written receivers
 */
class PeerEventListener implements SocketIoSocket.AllEventListener {

    private final Peer peer;
    private List<PeerEventHandlerEntry> peerEventHandlers = new ArrayList<>();

    PeerEventListener(Peer peer) {
        this.peer = peer;
    }

    /**
     * Register an event to execute if a message with the appropriate header is sent over the socket
     *
     * @param header  The header to listen to
     * @param handler Your written handler
     * @return ID of initialized event handler for removal purposes
     */
    public int addEventHandler(String header, PeerEventHandler handler) {
        peerEventHandlers.add(new PeerEventHandlerEntry(header, handler));
        return peerEventHandlers.size() - 1;
    }

    /**
     * Remove a handler based on it's given ID
     *
     * @param id ID of the handler that is to be removed
     */
    public void removeEventHandler(int id) {
        peerEventHandlers.set(id, null);
    }

    /**
     * Remove all event handlers based on it's header
     *
     * @param header Header to unregister
     */
    public void removeAllEventHandlers(String header) {
        for (int i = 0; i < peerEventHandlers.size(); i++) {
            if (peerEventHandlers.get(i).getHeader().equals(header)) {
                peerEventHandlers.set(i, null);
            }
        }
    }

    /**
     * Remove absolutely every event handler from this peer
     */
    public void removeAllEventHandlers() {
        peerEventHandlers.clear();
    }

    @Override
    public void event(String eventName, Object... args) {
        for (PeerEventHandlerEntry entry : peerEventHandlers) {
            if (entry.getHeader().equals(eventName)) {
                entry.getPeerEventHandler().exec(eventName, peer, new PeerEventData(args));
            }
        }
    }
}