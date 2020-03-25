package com.rtbytez.server.events.handlers;

import com.rtbytez.server.file.File;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventData;
import com.rtbytez.server.peer.PeerEventHandler;
import com.rtbytez.server.permissions.RoomAction;
import com.rtbytez.server.room.Room;
import com.rtbytez.server.room.RoomManager;
import com.rtbytez.server.util.MessageCreator;
import com.rtbytez.server.util.RTJSON;

public class FileEvent extends PeerEventHandler {

    public FileEvent() {
        super();
    }

    @Override
    public void exec(String header, Peer peer, PeerEventData data) {
        /* TODO: Check Peer Authentication */

        Room room = RoomManager.getRoomOf(peer);
        if (room == null) {
            peer.emit("file", MessageCreator.notInARoom());
            return;
        }

        RTJSON json = data.getAsRTJSON();

        switch (json.getString("type", "")) {
            case "create": { // Create a new file
                if (!room.hasPermissionTo(peer, RoomAction.CREATE_FILE)) {
                    peer.emit("file", MessageCreator.noPermission());
                    return;
                }
                if (!json.has("path")) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                String path = json.getString("path");
                if (room.getFileManager().doesFileExist(path)) {
                    peer.emit("file", MessageCreator.fileAlreadyExists());
                    return;
                }
                room.getFileIORouter().createFile(peer, path);
                peer.emit("file", MessageCreator.ok());
                return;
            }

            case "delete": { // Delete a file
                if (!room.hasPermissionTo(peer, RoomAction.DELETE_FILE)) {
                    peer.emit("file", MessageCreator.noPermission());
                    return;
                }
                if (!json.has("path")) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit("file", MessageCreator.fileDoesntExist());
                    return;
                }
                room.getFileIORouter().deleteFile(peer, path);
                peer.emit("file", MessageCreator.ok());
                return;
            }

            case "rename": { // Rename a file
                if (!room.hasPermissionTo(peer, RoomAction.RENAME_FILE)) {
                    peer.emit("file", MessageCreator.noPermission());
                    return;
                }
                if (!json.has("oldPath", "newPath")) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                String oldPath = json.getString("oldPath");
                String newPath = json.getString("newPath");
                if (!room.getFileManager().doesFileExist(oldPath)) {
                    peer.emit("file", MessageCreator.fileDoesntExist());
                    return;
                }
                if (room.getFileManager().doesFileExist(newPath)) {
                    peer.emit("file", MessageCreator.fileAlreadyExists());
                    return;
                }
                room.getFileIORouter().renameFile(peer, oldPath, newPath);
                return;
            }

            case "retrieve": { // Retrieve a file
                if (!room.hasPermissionTo(peer, RoomAction.RETRIEVE_FILE)) {
                    peer.emit("file", MessageCreator.noPermission());
                    return;
                }
                if (!json.has("path")) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit("file", MessageCreator.fileDoesntExist());
                    return;
                }
                File file = room.getFileManager().getFile(path);
                peer.emit("file", MessageCreator.fileRetrieve(room, path, file));
                return;
            }

            case "add": { // Add a line
                if (!room.hasPermissionTo(peer, RoomAction.ADD_LINE)) {
                    peer.emit("file", MessageCreator.noPermission());
                    return;
                }
                if (!json.has("path", "lineNumber")) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                if (!(json.get("lineNumber") instanceof Integer)) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit("file", MessageCreator.fileDoesntExist());
                    return;
                }
                int lineNumber = json.getInt("lineNumber");
                room.getFileIORouter().addLine(peer, path, lineNumber);
                return;
            }

            case "modify": { // Modify a line
                if (!room.hasPermissionTo(peer, RoomAction.MODIFY_LINE)) {
                    peer.emit("file", MessageCreator.noPermission());
                    return;
                }
                if (!json.has("path", "lineID", "text")) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit("file", MessageCreator.fileDoesntExist());
                    return;
                }
                String lineId = json.getString("lineId");
                if (room.getFileManager().getFile(path).getLineById(lineId) == null) {
                    peer.emit("file", MessageCreator.noSuchLineExists());
                    return;
                }
                String text = json.getString("text");
                room.getFileIORouter().modifyLine(peer, path, lineId, text);
                return;
            }

            case "remove": { // Remove a line
                if (!room.hasPermissionTo(peer, RoomAction.REMOVE_LINE)) {
                    peer.emit("file", MessageCreator.noPermission());
                    return;
                }
                if (!json.has("path", "lineNumber")) {
                    peer.emit("file", MessageCreator.invalidArguments());
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit("file", MessageCreator.fileDoesntExist());
                    return;
                }
                String lineId = json.getString("lineId");
                if (room.getFileManager().getFile(path).getLineById(lineId) == null) {
                    peer.emit("file", MessageCreator.noSuchLineExists());
                    return;
                }
                room.getFileIORouter().removeLine(peer, path, lineId);
                return;
            }
        }
    }
}
