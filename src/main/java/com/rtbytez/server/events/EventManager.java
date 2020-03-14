package com.rtbytez.server.events;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.rtbytez.server.Console;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

import java.util.List;

public class EventManager implements EventInterceptor {

    public EventManager() {
        Console.log("EventManager", "Registered with Server");
    }

    @Override
    public void onEvent(NamespaceClient client, String eventName, List<Object> args, AckRequest ackRequest) {
        Console.log("EventManager", "Received Event: " + eventName);
        Peer peer = PeerManager.getPeerBySocketId(client.getSessionId().toString());
        //TODO: Fix null check or add error throw to PeerManager#getPeerBySocketId
        peer.getEventListener().event(eventName, args.toArray());
    }
}
