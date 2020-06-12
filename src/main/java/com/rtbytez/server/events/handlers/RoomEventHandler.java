package com.rtbytez.server.events.handlers;

import com.rtbytez.common.comms.enums.RoomRole;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorGeneric;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorNoPermission;
import com.rtbytez.common.comms.packets.generic.ok.RTPOK;
import com.rtbytez.common.comms.packets.room.error.RTPRoomErrorAlreadyInARoom;
import com.rtbytez.common.comms.packets.room.error.RTPRoomErrorDoesntExist;
import com.rtbytez.common.comms.packets.room.error.RTPRoomErrorNotInARoom;
import com.rtbytez.common.comms.packets.room.request.RTPRoomRequestCreate;
import com.rtbytez.common.comms.packets.room.request.RTPRoomRequestJoin;
import com.rtbytez.common.comms.packets.room.request.RTPRoomRequestLeave;
import com.rtbytez.common.comms.packets.room.request.RTPRoomRequestState;
import com.rtbytez.common.comms.packets.room.response.RTPRoomJoined;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventHandler;
import com.rtbytez.server.permissions.ServerAction;
import com.rtbytez.server.permissions.ServerManager;
import com.rtbytez.server.room.Room;
import com.rtbytez.server.room.RoomManager;

public class RoomEventHandler extends PeerEventHandler {

    public RoomEventHandler() {
        super();
    }

    @Override
    public void exec(Peer peer, RTPacket packet) {

        Room room = RoomManager.getRoomOf(peer);

        if (packet instanceof RTPRoomRequestCreate) {

            if (room != null) {
                peer.emit(new RTPRoomErrorAlreadyInARoom("room"));
                return;
            }

            if (!ServerManager.hasPermission(peer, ServerAction.CREATE_ROOM)) {
                peer.emit(new RTPErrorNoPermission("room"));
                return;
            }

            Room newRoom = RoomManager.createRoom();
            newRoom.addMember(peer);
            peer.emit(new RTPRoomJoined("room", newRoom.getId()));
            newRoom.setRole(peer, RoomRole.OWNER);
            return;
        }

        if (packet instanceof RTPRoomRequestJoin) {
            RTPRoomRequestJoin rtpRoomRequestJoin = (RTPRoomRequestJoin) packet;

            if (room != null) {
                peer.emit(new RTPRoomErrorAlreadyInARoom("room"));
                return;
            }

            Room newRoom = RoomManager.getRoom(rtpRoomRequestJoin.getRoomId());

            if (newRoom == null) {
                peer.emit(new RTPRoomErrorDoesntExist("room"));
                return;
            }

            newRoom.addMember(peer);
            peer.emit(new RTPRoomJoined("room", newRoom.getId()));
            return;
        }

        if (packet instanceof RTPRoomRequestLeave) {

            if (room == null) {
                peer.emit(new RTPRoomErrorNotInARoom("room"));
                return;
            }

            room.removeMember(peer);
            peer.emit(new RTPOK("room"));
            return;
        }

        if (packet instanceof RTPRoomRequestState) {
            // Todo

            return;
        }

        peer.emit(new RTPErrorGeneric("room", "End of logic for Room"));
    }
}
