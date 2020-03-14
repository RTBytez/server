package com.rtbytez.server.events.io;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.rtbytez.server.Console;
import com.rtbytez.server.events.handlers.AuthenticateEvent;
import com.rtbytez.server.events.handlers.IncomingDisconnectEvent;
import com.rtbytez.server.events.handlers.PingEvent;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

public class ConnectionEvent implements ConnectListener {

    public ConnectionEvent() {
        Console.log("ConnectionEvent", "Registered with Server Namespace");
    }

    @Override
    public void onConnect(SocketIOClient socket) {
        Peer peer = PeerManager.getPeer(socket);
        Console.log("ConnectEvent", peer.getId() + " Connected");
        peer.on("ping", new PingEvent());
        peer.on("incomingDisconnect", new IncomingDisconnectEvent());
        peer.on("auth", new AuthenticateEvent());
    }
}
