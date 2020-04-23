package com.rtbytez.common.comms.packets.file.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPFileRemoveLine extends RTPacket {

    private final String roomId;
    private final String peerId;
    private final String filePath;
    private final String lineId;

    public RTPFileRemoveLine(String header, String roomId, String peerId, String filePath, String lineId) {
        super(header);
        setShortCode("FileAddLine");
        this.roomId = roomId;
        this.peerId = peerId;
        this.filePath = filePath;
        this.lineId = lineId;
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
}
