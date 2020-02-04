package com.rtbytez.server.events;

import io.socket.emitter.Emitter;
import io.socket.socketio.server.SocketIoNamespace;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;

public abstract class SocketEventHandler implements Emitter.Listener {

    /**
     * Execute when the event is emitted
     *
     * @param server    The server the event is associated to
     * @param namespace The namespace the event is associated to
     * @param socket    The socket the event is associated to
     */
    public abstract void exec(SocketIoServer server, SocketIoNamespace namespace, SocketIoSocket socket);

    public void call(Object... args) {
        SocketIoSocket socket = (SocketIoSocket) args[0];
        SocketIoNamespace namespace = socket.getNamespace();
        SocketIoServer server = namespace.getServer();
        exec(server, namespace, socket);
    }

}
