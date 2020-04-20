package com.rtbytez.server.common.comms.packets.file.broadcasts;

import com.rtbytez.server.common.comms.packets.RTPacket;

public class RTPFileDelete extends RTPacket {
    public RTPFileDelete(String header, String roomId, String peerId, String filePath) {
        super(header);
        setShortCode("FileDelete");
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
