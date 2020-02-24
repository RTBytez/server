package com.rtbytez.server.peer;

//TODO: Write and document later
public abstract class PeerEventHandler {

    public abstract void exec(String header, Peer peer, PeerEventData data);

}
