package com.rtbytez.server.room;

import com.rtbytez.server.peer.Peer;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {

    private static HashMap<String, Room> rooms = new HashMap<>();

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private RoomManager() {

    }

    /**
     * Create a room
     *
     * @return The room that is newly created
     */
    public static Room createRoom() {
        Room room = new Room();
        rooms.put(room.getId(), room);
        return room;
    }

    /**
     * Retrieve a room in memory
     *
     * @param id The id of the room to retrieve
     * @return The room that is retrieved
     */
    public static Room getRoom(String id) {
        return rooms.get(id);
    }

    /**
     * Retrieve the room of a peer
     *
     * @param peer Peer to search for in rooms
     * @return The room that peer is in or null
     */
    public static Room getRoomOf(Peer peer) {
        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            Room room = entry.getValue();
            if (room.hasMember(peer)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Delete a room by id
     *
     * @param id The id of the room to delete
     */
    public static void deleteRoom(String id) {
        rooms.remove(id);
    }

    /**
     * Retrieve the list of rooms in memory
     *
     * @return The list of rooms
     */
    public static HashMap<String, Room> getRooms() {
        return rooms;
    }


}
