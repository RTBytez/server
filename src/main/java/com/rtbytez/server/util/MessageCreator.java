package com.rtbytez.server.util;

import com.rtbytez.server.file.File;
import com.rtbytez.server.file.Line;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.permissions.RoomRole;
import com.rtbytez.server.room.Room;
import org.json.JSONObject;

import java.util.Map;

public class MessageCreator {

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private MessageCreator() {

    }

    // Success
    public static JSONObject ok() {
        return new JSONObject("{'error': false, 'short': 'ok'}");
    }

    public static JSONObject roomJoin(Room room, Peer peer) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'roomJoin'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("peerUsername", peer.getUsername());
        return jsonObject;
    }

    public static JSONObject roomRoleChange(Room room, Peer peer, RoomRole role) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'roomRoleChange'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("peerUsername", peer.getUsername());
        jsonObject.put("role", role.toString());
        return jsonObject;
    }

    public static JSONObject roomLeave(Room room, Peer peer) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'roomLeave'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("peerUsername", peer.getUsername());
        return jsonObject;
    }

    public static JSONObject fileRename(Room room, Peer peer, String oldPath, String newPath) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'fileRename'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("fileOldPath", oldPath);
        jsonObject.put("fileNewPath", newPath);
        return jsonObject;
    }

    public static JSONObject fileCreate(Room room, Peer peer, String filePath) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'fileCreate'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("filePath", filePath);
        return jsonObject;
    }

    public static JSONObject fileDelete(Room room, Peer peer, String filePath) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'fileDelete'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("filePath", filePath);
        return jsonObject;
    }

    public static JSONObject fileAddLine(Room room, Peer peer, String filePath, int lineNumber, String lineId) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'fileAddLine'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("filePath", filePath);
        jsonObject.put("lineNumber", lineNumber);
        jsonObject.put("lineId", lineId);
        return jsonObject;
    }

    public static JSONObject fileModifyLine(Room room, Peer peer, String filePath, String lineId, String text) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'fileModifyLine'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("filePath", filePath);
        jsonObject.put("lineId", lineId);
        jsonObject.put("text", text);
        return jsonObject;
    }

    public static JSONObject fileRemoveLine(Room room, Peer peer, String filePath, String lineId) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'fileDeleteLine'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("peerId", peer.getId());
        jsonObject.put("filePath", filePath);
        jsonObject.put("lineId", lineId);
        return jsonObject;
    }

    public static JSONObject fileRetrieve(Room room, String path, File file) {
        JSONObject jsonObject = new JSONObject("{'error': false, 'short': 'fileRetrieve'}");
        jsonObject.put("roomId", room.getId());
        jsonObject.put("filePath", path);
        JSONObject lines = new JSONObject();
        for (Map.Entry<String, Line> stringLineEntry : file.getLines().entrySet()) {
            JSONObject line = new JSONObject();
            line.put("lineNumber", stringLineEntry.getValue().getLineNumber());
            line.put("text", stringLineEntry.getValue().getText());
            lines.put(stringLineEntry.getKey(), line);
        }
        jsonObject.put("contents", lines);
        return jsonObject;
    }


    // ========================
    //         ERRORS
    // ========================

    // Generic Errors
    public static JSONObject notInARoom() {
        return new JSONObject("{'error': true, 'short': 'notInARoom', 'message': 'You do not belong to a room!'}");
    }

    public static JSONObject noPermission() {
        return new JSONObject("{'error': true, 'short': 'noPermission', 'message': 'You do not have permission to preform this action!'}");
    }

    public static JSONObject invalidArguments() {
        return new JSONObject("{'error': true, 'short': 'invalidArguments', 'message': 'Invalid arguments'}");
    }

    // File Errors
    public static JSONObject fileAlreadyExists() {
        return new JSONObject("{'error': true, 'short': 'fileAlreadyExists', 'message': 'File already exists'}");
    }

    public static JSONObject fileDoesntExist() {
        return new JSONObject("{'error': true, 'short': 'fileDoesntExist', 'message': 'File doesnt exist'}");
    }

    public static JSONObject noSuchLineExists() {
        return new JSONObject("{'error': true, 'short': 'noSuchLineExists', 'message': 'The line with the provided ID doesnt exist!'}");
    }

}
