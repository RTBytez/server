package com.rtbytez.server.peer;

import com.rtbytez.common.util.Console;

public abstract class PeerEventHandler {

    public PeerEventHandler() {
        Console.log("PeerEventHandler", "Registered " + this.getClass().getSimpleName());
    }

    /**
     * Execute when an event is emitted from the PeerEventListener
     *
     * @param header Frame header that was received
     * @param peer   Peer instance
     * @param data   Data from the the frame received
     */
    public abstract void exec(String header, Peer peer, PeerEventData data);

}
