package com.rtbytez.server.events.io;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.rtbytez.common.util.Console;
import com.rtbytez.server.events.handlers.*;
import com.rtbytez.server.events.middleware.MPacketValidator;
import com.rtbytez.server.events.middleware.MPeerInRoom;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

public class ConnectionEventHandler implements ConnectListener {

    public ConnectionEventHandler() {
        Console.log("ConnectionEvent", "Registered with Server Namespace");
    }

    @Override
    public void onConnect(SocketIOClient socket) {
        Peer peer = PeerManager.getPeer(socket);
        Console.log("ConnectEvent", peer.getShort() + " Connected");
        peer.on("echo", new EchoEventHandler());
        peer.on("auth", new AuthenticateEventHandler()); //TODO
        peer.on("file", new FileEventHandler(), new MPacketValidator(), new MPeerInRoom());
        peer.on("info", new InfoEventHandler(), new MPacketValidator());
        peer.on("room", new RoomEventHandler(), new MPacketValidator());
    }
}
