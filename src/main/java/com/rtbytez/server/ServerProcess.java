package com.rtbytez.server;

import com.rtbytez.server.events.ConnectionEvent;
import com.rtbytez.server.events.DisconnectionEvent;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;

public class ServerProcess {

    public static void main(String[] args) {
        SocketIOServerHost socketIOServerHost = new SocketIOServerHost();
        SocketIoServer socketIoServer = socketIOServerHost.getSocketIoServer();
        SocketIoNamespace namespace = socketIoServer.namespace("/");
        namespace.on("connection", new ConnectionEvent());
        namespace.on("disconnection", new DisconnectionEvent());

        //noinspection InfiniteLoopStatement,StatementWithEmptyBody
        while (true) {}
    }

}
