package com.rtbytez.server.util;

import java.util.UUID;

public class Functions {

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private Functions() {

    }

    /**
     * Return a random set of 8 characters in a row
     *
     * @return The random set of 8 characters
     */
    public static String s4() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 7);
    }

}
