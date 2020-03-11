package com.rtbytez.server;

public abstract class Debug {

    /**
     * Log a message to the console if debug mode is enabled
     *
     * @param message The message to log
     */
    public static void log(String message) {
        System.out.println(message);
    }

    /**
     * Log a message to the console if debug mode is enabled
     *
     * @param title   The title or origin of the message
     * @param message The message to log
     */
    public static void log(String title, String message) {
        System.out.println("[" + title + "] " + message);
    }

}
