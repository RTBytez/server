package com.rtbytez.common.comms.packets.file.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPFileModifyLine extends RTPacket {
    public RTPFileModifyLine(String header, String roomId, String peerId, String filePath, String lineId, String text) {
        super(header);
        setShortCode("FileAddLine");
        put("roomId", roomId);
        put("peerId", peerId);
        put("filePath", filePath);
        put("lineId", lineId);
        put("text", text);
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

    public String getText() {
        return getString("text");
    }
}
