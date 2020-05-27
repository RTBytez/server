package com.rtbytez.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.rtbytez.common.util.Console;
import com.rtbytez.server.events.PacketRouter;
import com.rtbytez.server.events.io.ConnectionEventHandler;
import com.rtbytez.server.events.io.DisconnectionEventHandler;
import com.rtbytez.server.events.io.ExceptionEventHandler;
import com.rtbytez.server.packethandler.RTBytezJsonSupport;

import java.util.Arrays;
import java.util.List;

import static com.rtbytez.common.util.Functions.disableLoggerFor;

/**
 * The birth of the RTBytez Server
 */
public class ServerProcess {

    public static void main(String[] rawArgs) {
        List<String> args = Arrays.asList(rawArgs);
        if (args.contains("help")) {

            System.out.println("RTBytez Server");
            System.out.println("Arguments are as follows");
            System.out.println("-bind=<IP> | Bind to an ip address (Default=0.0.0.0)");
            System.out.println("-port=<PORT> | Set server port number (Default=5623)");
            System.out.println("Example use: java -jar rtbytez-server.jar -bind=0.0.0.0 -port=5623");

            System.exit(0);
            return;
        }

        String address = "0.0.0.0";
        int port = 5623;

        for (String str : args) {
            if (str.startsWith("-bind=")) {
                address = str.substring(6);
            }
            if (str.contains("-port=")) {
                port = Integer.parseInt(str.substring(6));
            }
        }

        // Disable SocketIOServer Logger
        disableLoggerFor(SocketIOServer.class);

        Configuration config = new Configuration();
        config.setHostname(address);
        config.setPort(port);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        config.setJsonSupport(new RTBytezJsonSupport());
        config.setExceptionListener(new ExceptionEventHandler());
        SocketIOServer server = new SocketIOServer(config);
        Console.log("Server", "Starting on " + address + ":" + port + " ...");
        server.addConnectListener(new ConnectionEventHandler());
        server.addDisconnectListener(new DisconnectionEventHandler());
        server.addEventInterceptor(new PacketRouter());
        server.start();
        Console.log("Server", "Startup complete!");

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Console.log("Server process interrupted");
        }
    }
}
