package com.rtbytez.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.rtbytez.server.events.EventManager;
import com.rtbytez.server.events.io.ConnectionEvent;
import com.rtbytez.server.events.io.DisconnectionEvent;

/**
 * The birth of the RTBytez Server
 */
public class ServerProcess {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(1338);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        SocketIOServer server = new SocketIOServer(config);
        Console.log("SERVER", "Server process started");
        server.addConnectListener(new ConnectionEvent());
        server.addDisconnectListener(new DisconnectionEvent());
        server.getNamespace("").addEventInterceptor(new EventManager());
        //server.addEventInterceptor(new EventManager());
        server.start();
        Console.log("SERVER", "Server Startup Complete!");

        //noinspection InfiniteLoopStatement,StatementWithEmptyBody
        while (true) {
            // Moo. Don't stop the server process.
        }
    }

}
