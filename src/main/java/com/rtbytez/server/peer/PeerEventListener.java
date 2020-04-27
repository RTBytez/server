package com.rtbytez.server.peer;

import com.rtbytez.common.comms.packets.RTPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal Use Only - Handles events that are sent across the tunnel to written receivers
 */
public class PeerEventListener {

    private final Peer peer;
    private final List<PeerEventHandlerEntry> peerEventHandlers = new ArrayList<>();

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
     * Register an event to execute if a message with the appropriate header is sent over the socket
     *
     * @param header      The header to listen to
     * @param handler     Your written handler
     * @param middlewares Middleware handlers that will execute before the actual event handler
     * @return ID of initialized event handler for removal purposes
     */
    public int addEventHandler(String header, PeerEventHandler handler, PeerEventMiddleware... middlewares) {
        PeerEventHandlerEntry peerEventHandlerEntry = new PeerEventHandlerEntry(header, handler);
        for (PeerEventMiddleware middleware : middlewares) {
            peerEventHandlerEntry.addMiddleware(middleware);
        }
        peerEventHandlers.add(peerEventHandlerEntry);
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

    /**
     * Execute an event based on the registered listeners. To be used by the EventManager
     *
     * @param header The header of the event
     * @param data   The data
     */
    public void event(RTPacket packet) {
        for (PeerEventHandlerEntry entry : peerEventHandlers) {
            if (entry.getHeader().equals(packet.getHeader())) {
                for (PeerEventMiddleware middleware : entry.getMiddleware()) {
                    if (middleware.exec(peer, packet) != 0) {
                        return;
                    }
                }
                entry.getPeerEventHandler().exec(peer, packet);
            }
        }
    }
}