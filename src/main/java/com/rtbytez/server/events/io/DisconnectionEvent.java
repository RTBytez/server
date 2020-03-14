package com.rtbytez.server.events.io;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.rtbytez.server.Console;
import com.rtbytez.server.peer.PeerManager;

public class DisconnectionEvent implements DisconnectListener {

    public DisconnectionEvent() {
        Console.log("DisconnectionEvent", "Registered with Server Namespace");
    }

    @Override
    public void onDisconnect(SocketIOClient socket) {
        // The client has let the server know that an official disconnection has been decided and that this is not a timeout
        if (PeerManager.getPeer(socket).canSafeDisconnect()) {
            PeerManager.deletePeer(socket.getSessionId().toString());
        } else {
            //TODO: Timeout or Network Error
        }
    }
}
