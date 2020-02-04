package com.rtbytez.server;

import io.socket.engineio.server.EngineIoServer;
import io.socket.socketio.server.SocketIoServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocketIOServerHost extends HttpServlet {

    private final EngineIoServer engineIoServer = new EngineIoServer();
    private final SocketIoServer socketIoServer = new SocketIoServer(engineIoServer);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        engineIoServer.handleRequest(request, response);
    }

    public SocketIoServer getSocketIoServer() {
        return socketIoServer;
    }
}