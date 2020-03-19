package com.rtbytez.server.packethandler;

/**
 * @see RTBytezJsonSupport
 */
public class MessageObject {

    private final String message;

    public MessageObject(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
