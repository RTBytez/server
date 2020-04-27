package com.rtbytez.server.events.handlers;

import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.generic.error.RTPErrorGeneric;
import com.rtbytez.common.comms.packets.info.request.RTPInfoRequestPeerId;
import com.rtbytez.common.comms.packets.info.request.RTPInfoRequestSecret;
import com.rtbytez.common.comms.packets.info.response.RTPInfoPeerId;
import com.rtbytez.common.comms.packets.info.response.RTPInfoSecret;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventHandler;

public class InfoEvent extends PeerEventHandler {
    @Override
    public void exec(Peer peer, RTPacket packet) {

        if (packet instanceof RTPInfoRequestPeerId) {
            peer.emit(new RTPInfoPeerId("info", peer.getId()));
            return;
        }

        if (packet instanceof RTPInfoRequestSecret) {
            peer.emit(new RTPInfoSecret("info", peer.getSecret()));
            return;
        }

        peer.emit(new RTPErrorGeneric("info", "End of logic for Info"));
    }
}
