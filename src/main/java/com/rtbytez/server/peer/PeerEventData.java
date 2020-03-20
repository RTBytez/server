package com.rtbytez.server.peer;

import com.rtbytez.server.util.Console;
import org.json.JSONObject;

public class PeerEventData {

    final Object[] rawData;

    /**
     * Package-protected constructor - Only to be initialized by PeerEventListener
     */
    PeerEventData(Object... rawData) {
        this.rawData = rawData;
    }

    public boolean isThereData() {
        return rawData.length > 0;
    }

    /**
     * @return Data as string
     */
    public String getAsString() {
        if (isThereData()) {
            return rawData[0].toString();
        }
        return "";
    }

    /**
     * @return Data as JSONObject
     */
    public JSONObject getAsJson() {
        if (isThereData()) {
            try {
                JSONObject.testValidity(rawData[0]);
                return new JSONObject(rawData[0].toString());
            } catch (Exception e) {
                Console.error("PeerEventData", "There was a problem converting rawData into a JSONObject");
                Console.error("PeerEventData", e.getMessage());
            }

        }
        return new JSONObject();
    }

    @Override
    public String toString() {
        return getAsString();
    }
}
