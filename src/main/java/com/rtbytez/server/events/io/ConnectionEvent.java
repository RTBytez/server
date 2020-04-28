package com.rtbytez.server.events.io;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.rtbytez.common.util.Console;
import com.rtbytez.server.events.handlers.*;
import com.rtbytez.server.events.middleware.MPacketValidator;
import com.rtbytez.server.events.middleware.MPeerInRoom;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

public class ConnectionEvent implements ConnectListener {

    public ConnectionEvent() {
        Console.log("ConnectionEvent", "Registered with Server Namespace");
    }

    @Override
    public void onConnect(SocketIOClient socket) {
        Peer peer = PeerManager.getPeer(socket);
        Console.log("ConnectEvent", peer.getShort() + " Connected");
        peer.on("echo", new EchoEvent());
        peer.on("auth", new AuthenticateEvent()); //TODO
        peer.on("file", new FileEvent(), new MPacketValidator(), new MPeerInRoom());
        peer.on("info", new InfoEvent(), new MPacketValidator());
        peer.on("room", new RoomEvent(), new MPacketValidator());
    }
}
