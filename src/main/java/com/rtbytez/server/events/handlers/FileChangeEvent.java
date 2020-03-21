package com.rtbytez.server.events.handlers;

import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventData;
import com.rtbytez.server.peer.PeerEventHandler;
import com.rtbytez.server.util.RTJSON;

public class FileChangeEvent extends PeerEventHandler {

    public FileChangeEvent() {
        super();
    }

    @Override
    public void exec(String header, Peer peer, PeerEventData data) {
        /* TODO: Check Peer Authentication and Room Membership Here */
        RTJSON json = data.getAsRTJSON();
        switch (json.getString("type", "")) {
            case "new":


            case "remove":


            case "modify":


            case "delete":


            case "retrieve":


        }
    }
}
