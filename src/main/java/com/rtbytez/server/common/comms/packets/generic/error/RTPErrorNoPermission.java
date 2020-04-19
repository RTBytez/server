package com.rtbytez.server.common.comms.packets.generic.error;

import com.rtbytez.server.common.comms.packets.RTPacketError;

public class RTPErrorNoPermission extends RTPacketError {

    public RTPErrorNoPermission(String header) {
        super(header);
        setShortCode("ErrorNoPermission");
        setMessage("You do not have permission to perform this action");
    }
}
