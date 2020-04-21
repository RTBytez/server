package com.rtbytez.common.comms.packets.file.broadcasts;

import com.rtbytez.common.comms.packets.RTPacket;

public class RTPFileCreate extends RTPacket {

    private final String roomId;
    private final String peerId;
    private final String filePath;

    public RTPFileCreate(String header, String roomId, String peerId, String filePath) {
        super(header);
        setShortCode("FileCreate");
        this.roomId = roomId;
        this.peerId = peerId;
        this.filePath = filePath;
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
}
