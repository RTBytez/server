package com.rtbytez.server.events.handlers;

import com.rtbytez.common.comms.bundles.LineBundle;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorAlreadyExists;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorDoesntExist;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorLineDoesntExist;
import com.rtbytez.common.comms.packets.file.response.RTPFileRetrieve;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorInvalidArguments;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorNoPermission;
import com.rtbytez.common.comms.packets.generic.ok.RTPOK;
import com.rtbytez.common.comms.packets.room.error.RTPRoomErrorNotInARoom;
import com.rtbytez.common.util.RTJSON;
import com.rtbytez.server.file.File;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventData;
import com.rtbytez.server.peer.PeerEventHandler;
import com.rtbytez.server.permissions.RoomAction;
import com.rtbytez.server.room.Room;
import com.rtbytez.server.room.RoomManager;

import java.util.ArrayList;
import java.util.List;

public class FileEvent extends PeerEventHandler {

    public FileEvent() {
        super();
    }

    @Override
    public void exec(String header, Peer peer, PeerEventData data) {
        /* TODO: Check Peer Authentication */

        Room room = RoomManager.getRoomOf(peer);
        if (room == null) {
            peer.emit(new RTPRoomErrorNotInARoom("file"));
            return;
        }

        RTJSON json = data.getAsRTJSON();

        switch (json.getString("type", "")) {
            case "create": { // Create a new file
                if (!room.hasPermissionTo(peer, RoomAction.CREATE_FILE)) {
                    peer.emit(new RTPErrorNoPermission("file"));
                    return;
                }
                if (!json.has("path")) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                String path = json.getString("path");
                if (room.getFileManager().doesFileExist(path)) {
                    peer.emit(new RTPFileErrorAlreadyExists("file"));
                    return;
                }
                room.getFileIORouter().createFile(peer, path);
                peer.emit(new RTPOK("file"));
                return;
            }

            case "delete": { // Delete a file
                if (!room.hasPermissionTo(peer, RoomAction.DELETE_FILE)) {
                    peer.emit(new RTPErrorNoPermission("file"));
                    return;
                }
                if (!json.has("path")) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit(new RTPFileErrorDoesntExist("file"));
                    return;
                }
                room.getFileIORouter().deleteFile(peer, path);
                peer.emit(new RTPOK("file"));
                return;
            }

            case "rename": { // Rename a file
                if (!room.hasPermissionTo(peer, RoomAction.RENAME_FILE)) {
                    peer.emit(new RTPErrorNoPermission("file"));
                    return;
                }
                if (!json.has("oldPath", "newPath")) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                String oldPath = json.getString("oldPath");
                String newPath = json.getString("newPath");
                if (!room.getFileManager().doesFileExist(oldPath)) {
                    peer.emit(new RTPFileErrorDoesntExist("file"));
                    return;
                }
                if (room.getFileManager().doesFileExist(newPath)) {
                    peer.emit(new RTPFileErrorAlreadyExists("file"));
                    return;
                }
                room.getFileIORouter().renameFile(peer, oldPath, newPath);
                peer.emit(new RTPOK("file"));
                return;
            }

            case "retrieve": { // Retrieve a file
                if (!room.hasPermissionTo(peer, RoomAction.RETRIEVE_FILE)) {
                    peer.emit(new RTPErrorNoPermission("file"));
                    return;
                }
                if (!json.has("path")) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit(new RTPFileErrorDoesntExist("file"));
                    return;
                }
                File file = room.getFileManager().getFile(path);
                List<LineBundle> lineBundles = new ArrayList<>();
                file.getLines().forEach((s, line) -> lineBundles.add(new LineBundle(line.getId(), line.getLineNumber(), line.getText())));
                peer.emit(new RTPFileRetrieve("file", room.getId(), path, lineBundles));
                return;
            }

            case "add": { // Add a line
                if (!room.hasPermissionTo(peer, RoomAction.ADD_LINE)) {
                    peer.emit(new RTPErrorNoPermission("file"));
                    return;
                }
                if (!json.has("path", "lineNumber")) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                if (!(json.get("lineNumber") instanceof Integer)) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit(new RTPFileErrorDoesntExist("file"));
                    return;
                }
                int lineNumber = json.getInt("lineNumber");
                room.getFileIORouter().addLine(peer, path, lineNumber);
                peer.emit(new RTPOK("file"));
                return;
            }

            case "modify": { // Modify a line
                if (!room.hasPermissionTo(peer, RoomAction.MODIFY_LINE)) {
                    peer.emit(new RTPErrorNoPermission("file"));
                    return;
                }
                if (!json.has("path", "lineId", "text")) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit(new RTPFileErrorDoesntExist("file"));
                    return;
                }
                String lineId = json.getString("lineId");
                if (room.getFileManager().getFile(path).getLineById(lineId) == null) {
                    peer.emit(new RTPFileErrorLineDoesntExist("file"));
                    return;
                }
                String text = json.getString("text");
                room.getFileIORouter().modifyLine(peer, path, lineId, text);
                peer.emit(new RTPOK("file"));
                return;
            }

            case "remove": { // Remove a line
                if (!room.hasPermissionTo(peer, RoomAction.REMOVE_LINE)) {
                    peer.emit(new RTPErrorNoPermission("file"));
                    return;
                }
                if (!json.has("path", "lineId")) {
                    peer.emit(new RTPErrorInvalidArguments("file"));
                    return;
                }
                String path = json.getString("path");
                if (!room.getFileManager().doesFileExist(path)) {
                    peer.emit(new RTPFileErrorDoesntExist("file"));
                    return;
                }
                String lineId = json.getString("lineId");
                if (room.getFileManager().getFile(path).getLineById(lineId) == null) {
                    peer.emit(new RTPFileErrorLineDoesntExist("file"));
                    return;
                }
                room.getFileIORouter().removeLine(peer, path, lineId);
                peer.emit(new RTPOK("file"));
            }
        }
    }
}
