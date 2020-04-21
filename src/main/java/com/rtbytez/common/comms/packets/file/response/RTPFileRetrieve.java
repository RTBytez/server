package com.rtbytez.common.comms.packets.file.response;

import com.rtbytez.common.comms.bundles.LineBundle;
import com.rtbytez.common.comms.packets.RTPacket;

import java.util.List;

public class RTPFileRetrieve extends RTPacket {

    protected RTPFileRetrieve() {

    }

    public RTPFileRetrieve(String header, String roomId, String filePath, List<LineBundle> lines) {
        super(header);
        setShortCode("FileRetrieve");
        put("roomId", roomId);
        put("filePath", filePath);
        put("lines", lines);
    }

}
