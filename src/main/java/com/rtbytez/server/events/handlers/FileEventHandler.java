package com.rtbytez.server.events.handlers;

import com.rtbytez.common.comms.bundles.LineBundle;
import com.rtbytez.common.comms.enums.NoHashReason;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.file.broadcasts.RTPFileClear;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorAlreadyExists;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorDoesntExist;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorLineDoesntExist;
import com.rtbytez.common.comms.packets.file.request.*;
import com.rtbytez.common.comms.packets.file.response.RTPFileHash;
import com.rtbytez.common.comms.packets.file.response.RTPFileList;
import com.rtbytez.common.comms.packets.file.response.RTPFileNoHash;
import com.rtbytez.common.comms.packets.file.response.RTPFileRetrieve;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorGeneric;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorNoPermission;
import com.rtbytez.common.comms.packets.generic.ok.RTPOK;
import com.rtbytez.server.file.File;
import com.rtbytez.server.file.Line;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventHandler;
import com.rtbytez.server.permissions.RoomAction;
import com.rtbytez.server.room.Room;
import com.rtbytez.server.room.RoomManager;

import java.util.ArrayList;
import java.util.List;

public class FileEventHandler extends PeerEventHandler {

    public FileEventHandler() {
        super();
    }

    @Override
    public void exec(Peer peer, RTPacket packet) {
        /* TODO: Check Peer Authentication */

        Room room = RoomManager.getRoomOf(peer);
        assert room != null; // Since this is checked in middleware


        if (packet instanceof RTPFileRequestHash) {
            RTPFileRequestHash rtpFileRequestHash = (RTPFileRequestHash) packet;

            if (!room.hasPermissionTo(peer, RoomAction.RETRIEVE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestHash.getFilePath())) {
                peer.emit(new RTPFileNoHash("file", rtpFileRequestHash.getFilePath(), NoHashReason.NO_FILE));
                return;
            }

            peer.emit(new RTPFileHash("file", rtpFileRequestHash.getFilePath(), room.getFileManager().getFile(rtpFileRequestHash.getFilePath()).getHash()));
            return;
        }

        if (packet instanceof RTPFileRequestSetFile) {
            RTPFileRequestSetFile rtpFileRequestSetFile = (RTPFileRequestSetFile) packet;

            if (!room.hasPermissionTo(peer, RoomAction.MODIFY_LINE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestSetFile.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            room.broadcast(new RTPFileClear("file", rtpFileRequestSetFile.getFilePath()));

            int lineNumber = 1;
            for (String lineText : rtpFileRequestSetFile.getLines()) {
                Line line = room.getFileIORouter().addLine(peer, rtpFileRequestSetFile.getFilePath(), lineNumber);
                room.getFileIORouter().modifyLine(peer, rtpFileRequestSetFile.getFilePath(), line.getId(), lineText);
                lineNumber++;
            }
        }

        if (packet instanceof RTPFileRequestList) {

            if (!room.hasPermissionTo(peer, RoomAction.RETRIEVE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            peer.emit(new RTPFileList("file", room.getFileManager().getFileNames()));
            return;
        }

        if (packet instanceof RTPFileRequestCreate) {
            RTPFileRequestCreate rtpFileRequestCreate = (RTPFileRequestCreate) packet;

            if (!room.hasPermissionTo(peer, RoomAction.CREATE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (room.getFileManager().doesFileExist(rtpFileRequestCreate.getFilePath())) {
                peer.emit(new RTPFileErrorAlreadyExists("file"));
                return;
            }

            room.getFileIORouter().createFile(peer, rtpFileRequestCreate.getFilePath());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRequestDelete) {
            RTPFileRequestDelete rtpFileRequestDelete = (RTPFileRequestDelete) packet;

            if (!room.hasPermissionTo(peer, RoomAction.DELETE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestDelete.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }
            room.getFileIORouter().deleteFile(peer, rtpFileRequestDelete.getFilePath());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRequestRename) {
            RTPFileRequestRename rtpFileRequestRename = (RTPFileRequestRename) packet;

            if (!room.hasPermissionTo(peer, RoomAction.RENAME_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestRename.getOldPath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }
            if (room.getFileManager().doesFileExist(rtpFileRequestRename.getNewPath())) {
                peer.emit(new RTPFileErrorAlreadyExists("file"));
                return;
            }

            room.getFileIORouter().renameFile(peer, rtpFileRequestRename.getOldPath(), rtpFileRequestRename.getNewPath());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRequestRetrieve) {
            RTPFileRequestRetrieve rtpFileRequestRetrieve = (RTPFileRequestRetrieve) packet;

            if (!room.hasPermissionTo(peer, RoomAction.RETRIEVE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestRetrieve.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            File file = room.getFileManager().getFile(rtpFileRequestRetrieve.getFilePath());
            List<LineBundle> lineBundles = new ArrayList<>();
            file.getLines().forEach((s, line) -> lineBundles.add(new LineBundle(line.getId(), line.getLineNumber(), line.getText())));
            peer.emit(new RTPFileRetrieve("file", room.getId(), rtpFileRequestRetrieve.getFilePath(), file.getHash(), lineBundles));
            return;
        }

        if (packet instanceof RTPFileRequestAddLine) {
            RTPFileRequestAddLine rtpFileRequestAddLine = (RTPFileRequestAddLine) packet;

            if (!room.hasPermissionTo(peer, RoomAction.ADD_LINE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestAddLine.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            room.getFileIORouter().addLine(peer, rtpFileRequestAddLine.getFilePath(), rtpFileRequestAddLine.getLineNumber());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRequestModifyLine) {
            RTPFileRequestModifyLine rtpFileRequestModifyLine = (RTPFileRequestModifyLine) packet;

            if (!room.hasPermissionTo(peer, RoomAction.MODIFY_LINE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestModifyLine.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            if (room.getFileManager().getFile(rtpFileRequestModifyLine.getFilePath()).getLineById(rtpFileRequestModifyLine.getLineId()) == null) {
                peer.emit(new RTPFileErrorLineDoesntExist("file"));
                return;
            }

            room.getFileIORouter().modifyLine(peer, rtpFileRequestModifyLine.getFilePath(), rtpFileRequestModifyLine.getLineId(), rtpFileRequestModifyLine.getText());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRequestRemoveLine) {
            RTPFileRequestRemoveLine rtpFileRequestRemoveLine = (RTPFileRequestRemoveLine) packet;

            if (!room.hasPermissionTo(peer, RoomAction.REMOVE_LINE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRequestRemoveLine.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            if (room.getFileManager().getFile(rtpFileRequestRemoveLine.getFilePath()).getLineById(rtpFileRequestRemoveLine.getLineId()) == null) {
                peer.emit(new RTPFileErrorLineDoesntExist("file"));
                return;
            }

            room.getFileIORouter().removeLine(peer, rtpFileRequestRemoveLine.getFilePath(), rtpFileRequestRemoveLine.getLineId());
            peer.emit(new RTPOK("file"));
            return;
        }

        peer.emit(new RTPErrorGeneric("file", "End of logic for File"));
    }
}
