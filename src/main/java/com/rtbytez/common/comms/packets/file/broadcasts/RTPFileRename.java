package com.rtbytez.common.comms.packets.file.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPFileRename extends RTPacket {

    private final String roomId;
    private final String peerId;
    private final String fileOldPath;
    private final String fileNewPath;

    public RTPFileRename(String header, String roomId, String peerId, String fileOldPath, String fileNewPath) {
        super(header);
        setShortCode("FileRename");
        this.roomId = roomId;
        this.peerId = peerId;
        this.fileOldPath = fileOldPath;
        this.fileNewPath = fileNewPath;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPeerId() {
        return peerId;
    }

    public String getFileOldPath() {
        return fileOldPath;
    }

    public String getFileNewPath() {
        return fileNewPath;
    }
}
