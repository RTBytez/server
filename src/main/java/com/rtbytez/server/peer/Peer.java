package com.rtbytez.server.peer;

import io.socket.socketio.server.SocketIoSocket;
import org.json.JSONObject;

import java.util.UUID;

public class Peer {

    private String uuid;
    private String secret;
    private final SocketIoSocket socket;
    private final PeerEventListener eventListener;

    /**
     * Package-private constructor. Use PeerManager to retrieve & create peers
     *
     * @see PeerManager#getPeer(SocketIoSocket)
     */
    Peer(SocketIoSocket socket) {
        this.socket = socket;
        this.uuid = UUID.randomUUID().toString();
        this.secret = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        eventListener = new PeerEventListener(this);
        socket.registerAllEventListener(eventListener);
    }

    /**
     * Send a raw frame to a peer
     *
     * @param header Data header
     * @param data   Raw string data
     */
    public void emit(String header, String data) {
        socket.emit(header, data);
    }

    /**
     * Send a JSON frame to a peer
     *
     * @param header Data header
     * @param json   JSON data
     */
    public void emit(String header, JSONObject json) {
        socket.emit(header, json.toString());
    }

    /**
     * @see PeerEventListener#addEventHandler(String, PeerEventHandler)
     */
    public int registerEvent(String header, PeerEventHandler handler) {
        return eventListener.addEventHandler(header, handler);
    }

    /**
     * @see PeerEventListener#removeEventHandler(int)
     */
    public void removeEvent(int id) {
        eventListener.removeEventHandler(id);
    }

    /**
     * @see PeerEventListener#removeAllEventHandlers(String)
     */
    public void removeAllEventHandlers(String header) {
        eventListener.removeAllEventHandlers(header);
    }

    /**
     * @see PeerEventListener#removeAllEventHandlers()
     */
    public void removeAllEventHandlers() {
        eventListener.removeAllEventHandlers();
    }

    /**
     * Retrieve the peer's ID. This can be used with PeerManager#getPeer(String) to get this Peer instance
     *
     * @return ID of peer
     */
    public String getId() {
        return uuid;
    }

    /**
     * Retrieve the peer's Secret. This token is used to overwrite the socket just in case a drop and reconnection occurs.
     *
     * @return Secret of peer
     */
    public String getSecret() {
        return secret;
    }
}
