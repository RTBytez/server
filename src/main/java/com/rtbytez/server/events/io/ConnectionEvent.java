package com.rtbytez.server.events.io;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.rtbytez.common.util.Console;
import com.rtbytez.server.events.handlers.*;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

public class ConnectionEvent implements ConnectListener {

    public ConnectionEvent() {
        Console.log("ConnectionEvent", "Registered with Server Namespace");
    }

    @Override
    public void onConnect(SocketIOClient socket) {
        Peer peer = PeerManager.getPeer(socket);
        Console.log("ConnectEvent", peer.getShort() + " Connected");
        peer.on("echo", new EchoEvent());
        peer.on("incomingDisconnect", new IncomingDisconnectEvent());
        peer.on("auth", new AuthenticateEvent());
        peer.on("file", new FileEvent());
        peer.on("debug", new DebugEvent());
    }
}
