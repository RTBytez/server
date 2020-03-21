package com.rtbytez.server.room;

import java.util.HashMap;

public class RoomManager {

    private static HashMap<String, Room> rooms = new HashMap<>();

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private RoomManager() {

    }


    public static Room createRoom() {
        Room room = new Room();
        rooms.put(room.getId(), room);
        return room;
    }

    public static Room getRoom(String id) {
        return rooms.get(id);
    }

    public static void deleteRoom(String id) {
        rooms.remove(id);
    }


}
