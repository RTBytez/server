package com.rtbytez.server.room;

import com.rtbytez.common.comms.enums.RoomRole;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.room.broadcasts.RTPRoomJoin;
import com.rtbytez.common.comms.packets.room.broadcasts.RTPRoomLeave;
import com.rtbytez.common.comms.packets.room.broadcasts.RTPRoomRoleChange;
import com.rtbytez.server.file.FileIORouter;
import com.rtbytez.server.file.FileManager;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.permissions.RoomAction;
import com.rtbytez.server.util.Functions;
import org.json.JSONObject;

import java.util.HashMap;

public class Room {

    private final FileManager fileManager;
    private final FileIORouter fileIORouter;
    private final HashMap<Peer, RoomRole> members;
    public String id; // TODO: revert back to private final when dummy room is complete

    public Room() {
        this.fileManager = new FileManager();
        this.fileIORouter = new FileIORouter(this);
        members = new HashMap<>();
        this.id = Functions.s4();
    }

    /**
     * Add a peer to the room, remove them for other room if they are already in one.
     *
     * @param peer The peer who is joining the room
     */
    public void addMember(Peer peer) {
        Room foreignRoom = RoomManager.getRoomOf(peer);
        if (foreignRoom != null) {
            // A Peer cannot be in multiple rooms
            foreignRoom.removeMember(peer);
        }
        members.put(peer, RoomRole.VIEWER);
        this.broadcast(new RTPRoomJoin("room", this.id, peer.getId(), peer.getUsername()));
    }

    /**
     * TODO: Implement
     *
     * @param peer todo
     * @param role todo
     */
    public void setRole(Peer peer, RoomRole role) {
        this.broadcast(new RTPRoomRoleChange("room", this.getId(), peer.getId(), peer.getUsername(), role));
    }

    /**
     * Remove a peer from the room
     *
     * @param peer The peer to remove
     */
    public void removeMember(Peer peer) {
        //TODO: Check last member -> check for changes, push and delete room
        //TODO: Check if room owner -> promote highest member by seniority to room owner
        this.broadcast(new RTPRoomLeave("room", this.id, peer.getId(), peer.getUsername()));
        members.remove(peer);
    }

    /**
     * TODO: Implement
     *
     * @param peer   todo
     * @param action todo
     * @return todo
     */
    public boolean hasPermissionTo(Peer peer, RoomAction action) {
        //TODO: Implement in branch feature-permissions
        //For now, every peer has access to do anything in a room
        return true;
    }

    /**
     * Retrieve the ID of this room
     *
     * @return The ID of the room
     */
    public String getId() {
        return id;
    }

    /**
     * Get the file manager for this room
     *
     * @return The file manager
     */
    public FileManager getFileManager() {
        return fileManager;
    }

    /**
     * Check if this room has a peer
     *
     * @param peer The peer in question
     * @return True of that peer is in this room
     */
    public boolean hasMember(Peer peer) {
        return members.containsKey(peer);
    }

    /**
     * Broadcast a message to all peers inside this room
     *
     * @param header The header of the message
     * @param data   The data to send
     */
    public void broadcast(String header, JSONObject data) {
        members.forEach((peer, roomRole) -> peer.emit(header, data));
    }

    /**
     * Broadcast a message to all peers inside this room
     *
     * @param packet The packet in which to broadcast
     */
    public void broadcast(RTPacket packet) {
        members.forEach((peer, roomRole) -> peer.emit(packet));
    }

    /**
     * Broadcast a message to all peers inside this room except one peer.
     *
     * @param ignoredPeer Which peer to ignore
     * @param packet      The packet in which to broadcast
     */
    public void broadcastBut(Peer ignoredPeer, RTPacket packet) {
        members.forEach((peer, roomRole) -> {
            if (!peer.getId().equals(ignoredPeer.getId())) {
                peer.emit(packet);
            }
        });
    }

    /**
     * Retrieve this room's fileIO Router which handles file modifications from peers
     *
     * @return FileIO Router
     */
    public FileIORouter getFileIORouter() {
        return this.fileIORouter;
    }
}
