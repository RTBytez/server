package com.rtbytez.server.events.io;

import com.rtbytez.server.events.SocketEventHandler;
import com.rtbytez.server.peer.PeerManager;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;

public class DisconnectionEvent extends SocketEventHandler {
    public void exec(SocketIoServer server, SocketIoNamespace namespace, SocketIoSocket socket) {
        PeerManager.deletePeer(socket.getId());
    }
}
