package com.rtbytez.server.events.comms;

import com.rtbytez.server.events.SocketEventHandler;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;

public class PingEvent extends SocketEventHandler {
    public void exec(SocketIoServer server, SocketIoNamespace namespace, SocketIoSocket socket) {
        socket.send("Pong");
    }
}
