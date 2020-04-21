package com.rtbytez.common.comms.bundles;

import org.json.JSONObject;

public class LineBundle extends Bundle {

    private final String lineId;
    private final int lineNumber;
    private final String text;

    public LineBundle(String lineId, int lineNumber, String text) {
        this.lineId = lineId;
        this.lineNumber = lineNumber;
        this.text = text;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lineId", this.lineId);
        jsonObject.put("lineNumber", lineNumber);
        jsonObject.put("text", text);
        return jsonObject;
    }

    @Override
    public String getPreferredKey() {
        return lineId;
    }
}
