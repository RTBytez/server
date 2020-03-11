package com.rtbytez.server.peer;

import com.rtbytez.server.Debug;

public abstract class PeerEventHandler {

    /**
     * Execute when an event is emitted from the PeerEventListener
     *
     * @param header Frame header that was received
     * @param peer   Peer instance
     * @param data   Data from the the frame received
     */
    public abstract void exec(String header, Peer peer, PeerEventData data);

    public PeerEventHandler() {
        Debug.log("PeerEventHandler", "Registered " + this.getClass().getCanonicalName());
    }

}
