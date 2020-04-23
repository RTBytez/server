package com.rtbytez.common.comms.bundles;

public class LineBundle extends Bundle {

    private final String lineId;
    private final int lineNumber;
    private final String text;

    public LineBundle(String lineId, int lineNumber, String text) {
        this.lineId = lineId;
        this.lineNumber = lineNumber;
        this.text = text;
    }

    public String getLineId() {
        return lineId;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getText() {
        return text;
    }
}
