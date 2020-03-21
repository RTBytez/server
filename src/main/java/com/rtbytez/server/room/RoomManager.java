package com.rtbytez.server.room;

import java.util.HashMap;

public class RoomManager {

    private static HashMap<String, Room> rooms = new HashMap<>();

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private RoomManager() {

    }

    /**
     * Create a room in memory
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
     * Delete a room by id
     *
     * @param id The id of the room to delete
     */
    public static void deleteRoom(String id) {
        rooms.remove(id);
    }


}
