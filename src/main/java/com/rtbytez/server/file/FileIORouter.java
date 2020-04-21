package com.rtbytez.server.file;

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
        room.broadcast("fileChange", MessageCreator.fileCreate(room, peer, path));
    }

    public void deleteFile(Peer peer, String path) {
        this.fileManager.deleteFile(path);
        room.broadcast("fileChange", MessageCreator.fileDelete(room, peer, path));
    }

    public void renameFile(Peer peer, String oldPath, String newPath) {
        this.fileManager.renameFile(oldPath, newPath);
        room.broadcast("fileChange", MessageCreator.fileRename(room, peer, oldPath, newPath));
    }

    public void addLine(Peer peer, String path, int lineNumber) {
        Line line = this.fileManager.getFile(path).newLine(lineNumber);
        room.broadcast("fileChange", MessageCreator.fileAddLine(room, peer, path, lineNumber, line.getId()));
    }

    public void modifyLine(Peer peer, String path, String lineId, String text) {
        this.fileManager.getFile(path).getLineById(lineId).setText(text);
        room.broadcast("fileChange", MessageCreator.fileModifyLine(room, peer, path, lineId, text));
    }

    public void removeLine(Peer peer, String path, String lineId) {
        this.fileManager.getFile(path).deleteLineById(lineId);
        room.broadcast("fileChange", MessageCreator.fileRemoveLine(room, peer, path, lineId));
    }
}
