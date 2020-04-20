package com.rtbytez.server.common.comms.packets.file.broadcasts;

import com.rtbytez.server.common.comms.packets.RTPacket;

public class RTPFileCreate extends RTPacket {
    public RTPFileCreate(String header, String roomId, String peerId, String filePath) {
        super(header);
        setShortCode("FileCreate");
        put("roomId", roomId);
        put("peerId", peerId);
        put("filePath", filePath);
    }

    public String getRoomId() {
        return getString("roomId");
    }

    public String getPeerId() {
        return getString("peerId");
    }

    public String getFilePath() {
        return getString("filePath");
    }

}
