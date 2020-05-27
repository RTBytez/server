package com.rtbytez.server.events;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.rtbytez.common.comms.packets.PacketFactory;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.util.Console;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PacketRouter implements EventInterceptor {

    public PacketRouter() {
        Console.log("EventManager", "Registered with Server");
    }

    @Override
    public void onEvent(NamespaceClient client, String eventName, List<Object> args, AckRequest ackRequest) {
        Peer peer = PeerManager.getPeerBySocketId(client.getSessionId().toString());
        //TODO: Fix null check or add error throw to PeerManager#getPeerBySocketId
        Console.log("Packet", Objects.requireNonNull(peer).getShort() + " => {" + eventName + " " + Arrays.toString(args.toArray()) + "}");
        RTPacket packet = PacketFactory.createPacket(eventName, String.valueOf(args.get(0)));
        peer.getEventListener().event(packet);
    }
}
