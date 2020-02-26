package com.rtbytez.server.events.io;

import com.rtbytez.server.events.SocketEventHandler;
import com.rtbytez.server.events.handlers.AuthenticateEvent;
import com.rtbytez.server.events.handlers.IncomingDisconnectEvent;
import com.rtbytez.server.events.handlers.PingEvent;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;

public class ConnectionEvent extends SocketEventHandler {

    public void exec(SocketIoServer server, SocketIoNamespace namespace, SocketIoSocket socket) {
        Peer peer = PeerManager.getPeer(socket);
        peer.on("ping", new PingEvent());
        peer.on("incomingDisconnect", new IncomingDisconnectEvent());
        peer.on("auth", new AuthenticateEvent());
    }
}
