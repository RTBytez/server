package com.rtbytez.server.file;

import com.rtbytez.common.comms.packets.file.broadcasts.*;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.room.Room;

public class FileIORouter {

    private final Room room;
    private final FileManager fileManager;

    public FileIORouter(Room room) {
        this.room = room;
        this.fileManager = room.getFileManager();
    }

    public void createFile(Peer peer, String path) {
        this.fileManager.createFile(path);
        room.broadcast(new RTPFileCreate("file", room.getId(), peer.getId(), path));
    }

    public void deleteFile(Peer peer, String path) {
        this.fileManager.deleteFile(path);
        room.broadcast(new RTPFileDelete("file", room.getId(), peer.getId(), path));
    }

    public void renameFile(Peer peer, String oldPath, String newPath) {
        this.fileManager.renameFile(oldPath, newPath);
        room.broadcast(new RTPFileRename("file", room.getId(), peer.getId(), oldPath, newPath));
    }

    public void addLine(Peer peer, String path, int lineNumber) {
        Line line = this.fileManager.getFile(path).newLine(lineNumber);
        room.broadcast(new RTPFileAddLine("file", room.getId(), peer.getId(), path, line.getId(), lineNumber));
    }

    public void modifyLine(Peer peer, String path, String lineId, String text) {
        this.fileManager.getFile(path).getLineById(lineId).setText(text);
        room.broadcast(new RTPFileModifyLine("file", room.getId(), peer.getId(), path, lineId, text));
    }

    public void removeLine(Peer peer, String path, String lineId) {
        this.fileManager.getFile(path).deleteLineById(lineId);
        room.broadcast(new RTPFileRemoveLine("file", room.getId(), peer.getId(), path, lineId));
    }
}
