package com.rtbytez.common.comms.bundles;

import org.json.JSONObject;

public abstract class Bundle {

    public abstract JSONObject getJSON();

    public abstract String getPreferredKey();

}
