package com.rtbytez.server.peer;

import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.util.Console;

public abstract class PeerEventMiddleware {

    public PeerEventMiddleware() {
        Console.log("PeerEventMiddleware", "Registered " + this.getClass().getSimpleName());
    }

    /**
     * Handle a middleware execution
     *
     * @param peer   The peer associated with this event
     * @param packet The packet that was received in the event
     * @return 0 to continue and move on to the next middleware or the event handler, any other number to stop
     */
    public abstract int exec(Peer peer, RTPacket packet);

}
