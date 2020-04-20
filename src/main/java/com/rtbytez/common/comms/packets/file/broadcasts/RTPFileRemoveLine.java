package com.rtbytez.common.comms.packets.file.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPFileRemoveLine extends RTPacket {

    protected RTPFileRemoveLine() {
    }

    public RTPFileRemoveLine(String header, String roomId, String peerId, String filePath, String lineId) {
        super(header);
        setShortCode("FileAddLine");
        put("roomId", roomId);
        put("peerId", peerId);
        put("filePath", filePath);
        put("lineId", lineId);
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

    public String getLineId() {
        return getString("lineId");
    }
}
