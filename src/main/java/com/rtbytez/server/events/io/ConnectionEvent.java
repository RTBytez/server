package com.rtbytez.server.events.io;

import com.rtbytez.server.events.SocketEventHandler;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;

public class ConnectionEvent extends SocketEventHandler {

    public void exec(SocketIoServer server, SocketIoNamespace namespace, SocketIoSocket socket) {
        // TODO: Write PingEvent#exec method
    }
}
