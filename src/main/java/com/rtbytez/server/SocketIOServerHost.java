package com.rtbytez.server;

import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoServerOptions;
import io.socket.socketio.server.SocketIoServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.socket.engineio.server.EngineIoServerOptions.ALLOWED_CORS_ORIGIN_ALL;

public class SocketIOServerHost extends HttpServlet {

    public static final EngineIoServerOptions OPTIONS = EngineIoServerOptions.newFromDefault();

    static {
        OPTIONS.setCorsHandlingDisabled(false);
        OPTIONS.setPingTimeout(5000);
        OPTIONS.setPingInterval(25000);
        OPTIONS.setAllowedCorsOrigins(ALLOWED_CORS_ORIGIN_ALL);
        OPTIONS.setMaxTimeoutThreadPoolSize(20);
        OPTIONS.lock();
    }

    private final EngineIoServer engineIoServer = new EngineIoServer(OPTIONS);
    private final SocketIoServer socketIoServer = new SocketIoServer(engineIoServer);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        engineIoServer.handleRequest(request, response);
    }

    public SocketIoServer getSocketIoServer() {
        return socketIoServer;
    }
}