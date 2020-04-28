package com.rtbytez.server.permissions;

import com.rtbytez.server.peer.Peer;

public class ServerManager {

    //Todo: create static Server Manager that handles permissions

    public static boolean hasPermission(Peer peer, ServerAction serverAction) {
        //Todo: Create
        //For now, we will say that every peer has all permissions
        return true;
    }

}
