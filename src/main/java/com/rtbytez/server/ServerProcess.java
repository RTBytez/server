package com.rtbytez.server;

import com.rtbytez.server.events.comms.PingEvent;
import com.rtbytez.server.events.io.ConnectionEvent;
import com.rtbytez.server.events.io.DisconnectionEvent;
import com.rtbytez.server.socketio.SocketIOServerHost;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;

/**
 * The birth of the RTBytez Server
 */
public class ServerProcess {

    public static void main(String[] args) {
        SocketIOServerHost socketIOServerHost = new SocketIOServerHost();
        SocketIoServer socketIoServer = socketIOServerHost.getSocketIoServer();
        SocketIoNamespace namespace = socketIoServer.namespace("/");
        namespace.on("connection", new ConnectionEvent());
        namespace.on("disconnection", new DisconnectionEvent());
        namespace.on("ping", new PingEvent());

        //noinspection InfiniteLoopStatement,StatementWithEmptyBody
        while (true) {
            // Moo. Don't stop the server process.
        }
    }

}
