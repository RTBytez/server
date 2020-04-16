package com.rtbytez.server.peer;

import com.rtbytez.server.util.Console;

public abstract class PeerEventMiddleware {

    public PeerEventMiddleware() {
        Console.log("PeerEventMiddleware", "Registered " + this.getClass().getSimpleName());
    }

    /**
     * Handle a middleware execution
     *
     * @param header The header of this event
     * @param peer   The peer associated with this event
     * @param data   The data associated with this event
     * @return 0 to continue and move on to the next middleware or the event handler, any other number to stop
     */
    public abstract int exec(String header, Peer peer, PeerEventData data);

}
