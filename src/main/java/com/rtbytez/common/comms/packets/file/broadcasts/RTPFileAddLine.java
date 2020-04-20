package com.rtbytez.common.comms.packets.file.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPFileAddLine extends RTPacket {
    public RTPFileAddLine(String header, String roomId, String peerId, String filePath, String lineId, int lineNumber) {
        super(header);
        setShortCode("FileAddLine");
        put("roomId", roomId);
        put("peerId", peerId);
        put("filePath", filePath);
        put("lineId", lineId);
        put("lineNumber", lineNumber);
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

    public int getLineNumber() {
        return getInt("lineNumber");
    }
}
