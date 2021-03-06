package com.rtbytez.server.events.io;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.rtbytez.common.util.Console;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

public class DisconnectionEventHandler implements DisconnectListener {

    public DisconnectionEventHandler() {
        Console.log("DisconnectionEvent", "Registered with Server Namespace");
    }

    @Override
    public void onDisconnect(SocketIOClient socket) {
        Peer peer = PeerManager.getPeer(socket);

        // The client has let the server know that an official disconnection has been decided and that this is not a timeout
        if (peer.canSafeDisconnect()) {
            Console.log("DisconnectEvent", peer.getShort() + " Disconnected gracefully");
            PeerManager.deletePeer(socket.getSessionId().toString());
        } else {
            Console.log("Disconnect Event", peer.getShort() + " Disconnected");
            //TODO: Timeout or Network Error
        }
    }
}