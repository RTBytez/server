package com.rtbytez.server.peer;

import org.json.JSONObject;

public class PeerEventData {

    Object[] rawData;

    /**
     * Package-protected constructor - Only to be initialized by PeerEventListener
     */
    PeerEventData(Object[] rawData) {
        this.rawData = rawData;
    }

    /**
     * @return Data as string
     */
    public String getAsString() {
        return (String) rawData[0];
    }

    /**
     * @return Data as JSONObject
     */
    public JSONObject getAsJson() {
        return new JSONObject((String) rawData[0]);
    }
}
