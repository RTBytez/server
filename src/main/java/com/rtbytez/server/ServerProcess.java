package com.rtbytez.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.rtbytez.server.events.EventManager;
import com.rtbytez.server.events.io.ConnectionEvent;
import com.rtbytez.server.events.io.DisconnectionEvent;
import com.rtbytez.server.packethandler.RTBytezJsonSupport;

/**
 * The birth of the RTBytez Server
 */
public class ServerProcess {

    public static void main(String[] args) {
        //TODO: Make this mess look better and make args instead of hard-coded values
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(1338);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        config.setJsonSupport(new RTBytezJsonSupport());
        SocketIOServer server = new SocketIOServer(config);
        Console.log("SERVER", "Starting...");
        server.addConnectListener(new ConnectionEvent());
        server.addDisconnectListener(new DisconnectionEvent());
        server.addEventInterceptor(new EventManager());
        server.start();
        Console.log("SERVER", "Startup complete!");

        //noinspection InfiniteLoopStatement,StatementWithEmptyBody
        while (true) {
            // Moo. Don't stop the server process.
        }
    }

}
