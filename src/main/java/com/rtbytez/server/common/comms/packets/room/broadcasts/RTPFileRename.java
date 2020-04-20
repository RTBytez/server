package com.rtbytez.server.common.comms.packets.room.broadcasts;

import com.rtbytez.server.common.comms.packets.RTPacket;

public class RTPFileRename extends RTPacket {
    public RTPFileRename(String header, String roomId, String peerId, String fileOldPath, String fileNewPath) {
        super(header);
        setShortCode("FileRename");
        put("roomId", roomId);
        put("peerId", peerId);
        put("fileOldPath", fileOldPath);
        put("fileNewPath", fileNewPath);
    }

    public String getRoomId() {
        return getString("roomId");
    }

    public String getPeerId() {
        return getString("peerId");
    }

    public String getFileOldPath() {
        return getString("fileOldPath");
    }

    public String getFileNewPath() {
        return getString("fileNewPath");
    }
}
