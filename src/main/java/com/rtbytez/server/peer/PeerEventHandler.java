package com.rtbytez.server.peer;

import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.util.Console;

public abstract class PeerEventHandler {

    public PeerEventHandler() {
        Console.log("PeerEventHandler", "Registered " + this.getClass().getSimpleName());
    }

    /**
     * Execute when an event is emitted from the PeerEventListener
     *
     * @param peer   Peer instance
     * @param packet The packet that was received in the event
     */
    public abstract void exec(Peer peer, RTPacket packet);

}
