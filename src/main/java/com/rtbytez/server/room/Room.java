package com.rtbytez.server.room;

import com.rtbytez.server.file.FileManager;
import com.rtbytez.server.util.Functions;

public class Room {

    private FileManager fileManager;
    private String id;


    public Room() {
        this.fileManager = new FileManager();
        this.id = Functions.s4();
    }


    public void addMember() {
        //TODO: Create
    }

    public void removeMember() {
        //TODO: Create
    }


    public String getId() {
        return id;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
