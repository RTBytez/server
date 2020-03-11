package com.rtbytez.server.events;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

import java.util.List;

public class EventManager implements EventInterceptor {

    @Override
    public void onEvent(NamespaceClient client, String eventName, List<Object> args, AckRequest ackRequest) {
        Peer peer = PeerManager.getPeer(client.getSessionId().toString());
        peer.getEventListener().event(eventName, args.toArray());
    }
}
