package com.rtbytez.server.events.io;

import com.rtbytez.server.events.SocketEventHandler;
import com.rtbytez.server.events.comms.PingEvent;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;

public class DisconnectionEvent extends SocketEventHandler {
    public void exec(SocketIoServer server, SocketIoNamespace namespace, SocketIoSocket socket) {
        socket.send("Pong");
    }
}
