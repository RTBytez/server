package com.rtbytez.server.util;

import java.util.UUID;

public class Functions {

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private Functions() {

    }

    public static String s4() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 7);
    }

}
