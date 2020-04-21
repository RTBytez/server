package com.rtbytez.common.comms.packets.file.response;

import com.rtbytez.common.comms.bundles.LineBundle;
import com.rtbytez.common.comms.packets.RTPacket;

import java.util.List;

public class RTPFileRetrieve extends RTPacket {

    private final String roomId;
    private final String filePath;

    public RTPFileRetrieve(String header, String roomId, String filePath, List<LineBundle> lines) {
        super(header);
        setShortCode("FileRetrieve");
        this.roomId = roomId;
        this.filePath = filePath;
    }
}