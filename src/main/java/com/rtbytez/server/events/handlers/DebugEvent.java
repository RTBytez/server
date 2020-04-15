package com.rtbytez.server.events.handlers;

import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventData;
import com.rtbytez.server.peer.PeerEventHandler;
import com.rtbytez.server.room.Room;
import com.rtbytez.server.room.RoomManager;
import com.rtbytez.server.util.RTJSON;

public class DebugEvent extends PeerEventHandler {

    public DebugEvent() {
        super();
    }

    @Override
    public void exec(String header, Peer peer, PeerEventData data) {
        RTJSON json = data.getAsRTJSON();
        if (json.getBoolean("createDummyRoom", false)) {
            Room room = RoomManager.createRoom();
            room.addMember(peer);
            peer.emit("debug", "Created and joined room with id: " + room.getId());
        }
        if (json.has("joinRoom")) {
            Room room = RoomManager.getRoom(json.getString("joinRoom"));
            room.addMember(peer);
            peer.emit("debug", "Joined room with id: " + room.getId());
        }

    }
}
