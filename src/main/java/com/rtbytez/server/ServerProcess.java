package com.rtbytez.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.rtbytez.server.events.EventManager;
import com.rtbytez.server.events.io.ConnectionEvent;
import com.rtbytez.server.events.io.DisconnectionEvent;
import com.rtbytez.server.packethandler.RTBytezJsonSupport;
import com.rtbytez.server.util.Console;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * The birth of the RTBytez Server
 */
public class ServerProcess {

    public static void main(String[] rawArgs) {
        List<String> args = Arrays.asList(rawArgs);
        if (String.join(" ", args).contains("help")) {

            System.out.println("RTBytez Server");
            System.out.println("Arguments are as follows");
            System.out.println("-bind=<IP> | Bind to an ip address (Default=All Interfaces)");
            System.out.println("-port=<PORT> | Set server port number (Default=5623)");
            System.out.println("Example use: java -jar rtbytez-server.jar -bind=127.0.0.1 -port=5623");

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
        SimpleLogger logger = (SimpleLogger) LoggerFactory.getLogger(SocketIOServer.class);
        try {
            Field field = SimpleLogger.class.getDeclaredField("currentLogLevel");
            field.setAccessible(true);
            field.set(logger, 25);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Configuration config = new Configuration();
        config.setHostname(address);
        config.setPort(port);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        config.setJsonSupport(new RTBytezJsonSupport());
        SocketIOServer server = new SocketIOServer(config);
        Console.log("SERVER", "Starting on " + address + ":" + port + " ...");
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
