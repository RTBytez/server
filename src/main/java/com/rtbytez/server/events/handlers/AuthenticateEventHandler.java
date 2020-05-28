package com.rtbytez.server.events.handlers;

import com.rtbytez.common.comms.enums.LoginResponseCode;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.auth.request.RTPAuthRequestLogin;
import com.rtbytez.common.comms.packets.auth.response.RTPAuthLoginResponse;
import com.rtbytez.server.peer.Peer;
import com.rtbytez.server.peer.PeerEventHandler;

public class AuthenticateEventHandler extends PeerEventHandler {

    public AuthenticateEventHandler() {
        super();
    }

    @Override
    public void exec(Peer peer, RTPacket packet) {
        if (packet instanceof RTPAuthRequestLogin) {
            RTPAuthRequestLogin rtpAuthRequestLogin = (RTPAuthRequestLogin) packet;
            //Todo: actually implement with authentication system
            peer.emit(new RTPAuthLoginResponse("auth", LoginResponseCode.SUCCESS));
        }

    }
}
