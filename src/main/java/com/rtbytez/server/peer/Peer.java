package com.rtbytez.server.peer;

import com.corundumstudio.socketio.SocketIOClient;
import com.rtbytez.server.util.Console;
import org.json.JSONObject;

import java.util.UUID;

public class Peer {

    private final SocketIOClient socket;
    private final PeerEventListener eventListener;
    private final String uuid;
    private boolean canSafeDisconnect = false;
    private String secret;

    /**
     * Package-private constructor. Use PeerManager to retrieve & create peers.
     *
     * @see PeerManager#getPeer(SocketIOClient)
     */
    Peer(SocketIOClient socket) {
        this.socket = socket;
        this.uuid = UUID.randomUUID().toString();
        this.secret = UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString();
        eventListener = new PeerEventListener(this);
    }

    /**
     * Send an empty data frame to a peer
     *
     * @param header Header of frame
     */
    public void emit(String header) {
        this.rawEmit(header, "");
    }

    /**
     * Send a string frame to a peer
     *
     * @param header Header of frame
     * @param data   Raw string data
     */
    public void emit(String header, String data) {
        this.rawEmit(header, data);
    }

    /**
     * Send a JSON frame to a peer
     *
     * @param header Header of frame
     * @param json   JSON data
     */
    public void emit(String header, JSONObject json) {
        this.rawEmit(header, json);
    }

    /**
     * Send a raw frame to a peer
     *
     * @param header Header of frame
     * @param data   Data of frame
     */
    public void rawEmit(String header, Object data) {
        Console.log("Packet", this.getShort() + " <= {" + header + " [" + data.toString() + "]}");
        socket.sendEvent(header, data.toString());
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
     * Retrieve the peer's nick. This is used for short hand purposes.
     *
     * @return Nick of Peer
     */
    public String getNick() {
        return uuid.substring(0, 7);
    }

    /**
     * Retrieve the peer's short ID. This ID is used for identification purposes in the console.
     *
     * @return Short ID of peer
     */
    public String getShort() {
        return this.getNick() + "@" + this.getAddress();
    }

    /**
     * Retrieve the peer's Socket ID. This can be used with PeerManager#getPeerBySocketId(String)
     * This is to only be used in raw IO communications such as connect and disconnect events
     *
     * @return Socket ID of peer
     */
    public String getSocketId() {
        return socket.getSessionId().toString();
    }

    /**
     * Retrieve the peer's IP.
     *
     * @return Ip Address of Peer
     */
    public String getAddress() {
        return socket.getRemoteAddress().toString().substring(1).replaceAll(":.*", "");
    }

    /**
     * Retrieve the peer's Event Listener
     *
     * @return Event Listener of Peer
     */
    public PeerEventListener getEventListener() {
        return eventListener;
    }

    /**
     * Retrieve the peer's Secret.
     * This token is used to overwrite the socket just in case a drop and reconnection occurs.
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
