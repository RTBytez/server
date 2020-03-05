package com.rtbytez.server.peer;

import io.socket.socketio.server.SocketIoSocket;
import org.json.JSONObject;

import java.util.UUID;

public class Peer {

    private final SocketIoSocket socket;
    private final PeerEventListener eventListener;
    private boolean canSafeDisconnect = false;
    private final String uuid;
    private String secret;

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
     * @param header Header of frame
     * @param data   Raw string data
     */
    public void emit(String header, String data) {
        socket.emit(header, data);
    }

    /**
     * Send a JSON frame to a peer
     *
     * @param header Header of frame
     * @param json   JSON data
     */
    public void emit(String header, JSONObject json) {
        socket.emit(header, json.toString());
    }

    /**
     * Send an empty data frame to a peer
     *
     * @param header Header of frame
     */
    public void emit(String header) {
        socket.emit(header, "");
    }

    /**
     * @see PeerEventListener#addEventHandler(String, PeerEventHandler)
     */
    @SuppressWarnings("UnusedReturnValue")
    public int on(String header, PeerEventHandler handler) {
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

    /**
     * Has the client notified the server of a disconnection
     *
     * @return True if the client has told the server if a known disconnection is going to take place
     */
    public boolean canSafeDisconnect() {
        return canSafeDisconnect;
    }

    /**
     * Sets if the client is going to issue a disconnect
     *
     * @param canSafeDisconnect true if a known disconnect is going to take place
     */
    public void setSafeDisconnect(boolean canSafeDisconnect) {
        this.canSafeDisconnect = canSafeDisconnect;
    }
}
