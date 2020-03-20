package com.rtbytez.server.events;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;
import com.rtbytez.server.util.Console;

import java.util.Arrays;
import java.util.List;

public class EventManager implements EventInterceptor {

    public EventManager() {
        Console.log("EventManager", "Registered with Server");
    }

    @Override
    public void onEvent(NamespaceClient client, String eventName, List<Object> args, AckRequest ackRequest) {
        Peer peer = PeerManager.getPeerBySocketId(client.getSessionId().toString());
        //TODO: Fix null check or add error throw to PeerManager#getPeerBySocketId
        Console.log("Packet", peer.getShort() + " => {" + eventName + " " + Arrays.toString(args.toArray()) + "}");
        peer.getEventListener().event(eventName, args.toArray());
    }
}
