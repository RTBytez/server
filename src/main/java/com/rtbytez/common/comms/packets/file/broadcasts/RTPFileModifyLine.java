package com.rtbytez.common.comms.packets.file.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPFileModifyLine extends RTPacket {

    private final String roomId;
    private final String peerId;
    private final String filePath;
    private final String lineId;
    private final String text;

    public RTPFileModifyLine(String header, String roomId, String peerId, String filePath, String lineId, String text) {
        super(header);
        setShortCode("FileAddLine");
        this.roomId = roomId;
        this.peerId = peerId;
        this.filePath = filePath;
        this.lineId = lineId;
        this.text = text;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPeerId() {
        return peerId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getLineId() {
        return lineId;
    }

    public String getText() {
        return text;
    }
}
