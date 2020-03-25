package com.rtbytez.server.room;

import com.rtbytez.server.file.FileIORouter;
import com.rtbytez.server.file.FileManager;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.permissions.RoomAction;
import com.rtbytez.server.permissions.RoomRole;
import com.rtbytez.server.util.Functions;
import com.rtbytez.server.util.MessageCreator;
import org.json.JSONObject;

import java.util.HashMap;

public class Room {

    private FileManager fileManager;
    private FileIORouter fileIORouter;
    private HashMap<Peer, RoomRole> members;
    private String id;

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
        this.broadcast("room", MessageCreator.roomJoin(this, peer));
    }

    /**
     * TODO: Implement
     *
     * @param peer
     * @param role
     */
    public void setRole(Peer peer, RoomRole role) {
        //TODO: Implement in branch feature-permissions
    }

    /**
     * Remove a peer from the room
     *
     * @param peer The peer to remove
     */
    public void removeMember(Peer peer) {
        //TODO: Check last member -> check for changes, push and delete room
        //TODO: Check if room owner -> promote highest member by seniority to room owner
        this.broadcast("room", MessageCreator.roomLeave(this, peer));
        members.remove(peer);
    }

    /**
     * TODO: Implement
     *
     * @param peer
     * @param action
     * @return
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
