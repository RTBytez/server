package com.rtbytez.server.events.handlers;

import com.rtbytez.common.comms.bundles.LineBundle;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.file.broadcasts.*;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorAlreadyExists;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorDoesntExist;
import com.rtbytez.common.comms.packets.file.error.RTPFileErrorLineDoesntExist;
import com.rtbytez.common.comms.packets.file.response.RTPFileRetrieve;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorGeneric;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorInvalidPacket;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorNoPermission;
import com.rtbytez.common.comms.packets.generic.ok.RTPOK;
import com.rtbytez.server.file.File;
import com.rtbytez.server.peer.Peer;
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
    public void exec(Peer peer, RTPacket packet) {
        /* TODO: Check Peer Authentication */

        Room room = RoomManager.getRoomOf(peer);
        assert room != null; // Since this is checked in middleware

        if (!packet.isValid()) {
            peer.emit(new RTPErrorInvalidPacket("file"));
            return;
        }

        if (packet instanceof RTPFileCreate) {
            RTPFileCreate rtpFileCreate = (RTPFileCreate) packet;

            if (!room.hasPermissionTo(peer, RoomAction.CREATE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (room.getFileManager().doesFileExist(rtpFileCreate.getFilePath())) {
                peer.emit(new RTPFileErrorAlreadyExists("file"));
                return;
            }

            room.getFileIORouter().createFile(peer, rtpFileCreate.getFilePath());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileDelete) {
            RTPFileDelete rtpFileDelete = (RTPFileDelete) packet;

            if (!room.hasPermissionTo(peer, RoomAction.DELETE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileDelete.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }
            room.getFileIORouter().deleteFile(peer, rtpFileDelete.getFilePath());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRename) {
            RTPFileRename rtpFileDelete = (RTPFileRename) packet;

            if (!room.hasPermissionTo(peer, RoomAction.RENAME_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileDelete.getFileOldPath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }
            if (room.getFileManager().doesFileExist(rtpFileDelete.getFileNewPath())) {
                peer.emit(new RTPFileErrorAlreadyExists("file"));
                return;
            }

            room.getFileIORouter().renameFile(peer, rtpFileDelete.getFileOldPath(), rtpFileDelete.getFileNewPath());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRetrieve) {
            RTPFileRetrieve rtpFileRetrieve = (RTPFileRetrieve) packet;

            if (!room.hasPermissionTo(peer, RoomAction.RETRIEVE_FILE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRetrieve.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            File file = room.getFileManager().getFile(rtpFileRetrieve.getFilePath());
            List<LineBundle> lineBundles = new ArrayList<>();
            file.getLines().forEach((s, line) -> lineBundles.add(new LineBundle(line.getId(), line.getLineNumber(), line.getText())));
            peer.emit(new RTPFileRetrieve("file", room.getId(), rtpFileRetrieve.getFilePath(), lineBundles));
            return;
        }

        if (packet instanceof RTPFileAddLine) {
            RTPFileAddLine rtpFileAddLine = (RTPFileAddLine) packet;

            if (!room.hasPermissionTo(peer, RoomAction.ADD_LINE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileAddLine.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            room.getFileIORouter().addLine(peer, rtpFileAddLine.getFilePath(), rtpFileAddLine.getLineNumber());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileModifyLine) {
            RTPFileModifyLine rtpFileModifyLine = (RTPFileModifyLine) packet;

            if (!room.hasPermissionTo(peer, RoomAction.MODIFY_LINE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileModifyLine.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            if (room.getFileManager().getFile(rtpFileModifyLine.getFilePath()).getLineById(rtpFileModifyLine.getLineId()) == null) {
                peer.emit(new RTPFileErrorLineDoesntExist("file"));
                return;
            }

            room.getFileIORouter().modifyLine(peer, rtpFileModifyLine.getFilePath(), rtpFileModifyLine.getLineId(), rtpFileModifyLine.getText());
            peer.emit(new RTPOK("file"));
            return;
        }

        if (packet instanceof RTPFileRemoveLine) {
            RTPFileRemoveLine rtpFileRemoveLine = (RTPFileRemoveLine) packet;

            if (!room.hasPermissionTo(peer, RoomAction.REMOVE_LINE)) {
                peer.emit(new RTPErrorNoPermission("file"));
                return;
            }

            if (!room.getFileManager().doesFileExist(rtpFileRemoveLine.getFilePath())) {
                peer.emit(new RTPFileErrorDoesntExist("file"));
                return;
            }

            if (room.getFileManager().getFile(rtpFileRemoveLine.getFilePath()).getLineById(rtpFileRemoveLine.getLineId()) == null) {
                peer.emit(new RTPFileErrorLineDoesntExist("file"));
                return;
            }

            room.getFileIORouter().removeLine(peer, rtpFileRemoveLine.getFilePath(), rtpFileRemoveLine.getLineId());
            peer.emit(new RTPOK("file"));
            return;
        }

        peer.emit(new RTPErrorGeneric("file", "End of logic for File"));
    }
}
