package com.rtbytez.server.peer;

import io.socket.socketio.server.SocketIoSocket;
import org.json.JSONObject;

public class Peer {

    private final SocketIoSocket socket;
    private final PeerEventListener eventListener;

    /**
     * Package-private constructor. Use PeerManager to retrieve & create peers
     *
     * @see PeerManager#getPeer(SocketIoSocket)
     */
    Peer(SocketIoSocket socket) {
        this.socket = socket;
        eventListener = new PeerEventListener(this);
        socket.registerAllEventListener(eventListener);
        //TODO: finish
    }

    /**
     * TODO: write
     * @param header
     * @param data
     */
    public void emit(String header, String data) {
        socket.emit(header, data);
    }

    /**
     * TODO: write
     * @param header
     * @param json
     */
    public void emit(String header, JSONObject json) {
        socket.emit(header, json.toString());
    }

    //TODO: create interface between this and PeerEventListener (eventListener)

}
