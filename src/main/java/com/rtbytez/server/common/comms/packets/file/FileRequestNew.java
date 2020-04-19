package com.rtbytez.server.common.comms.packets.file;

import com.rtbytez.server.common.comms.packets.RTPacket;

public class FileRequestNew extends RTPacket {
    public FileRequestNew(String header, String filePath, int lineNumber) {
        super(header);
        setShortCode("FileRequestNew");
        put("filePath", filePath);
        put("lineNumber", lineNumber);
    }

    public String getFilePath() {
        return getString("filePath");
    }

    public int getLineNumber() {
        return getInt("lineNumber");
    }
}
