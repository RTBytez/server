package com.rtbytez.server.peer;

import io.socket.socketio.server.SocketIoSocket;

import java.util.HashMap;
import java.util.Map;

public class PeerManager {

    private static HashMap<String, Peer> peers = new HashMap<>();

    /**
     * TODO: document
     *
     * @param socket
     * @return
     */
    public static Peer getPeer(SocketIoSocket socket) {
        for (Map.Entry<String, Peer> entry : peers.entrySet()) {
            if (entry.getKey().equals(socket.getId())) {
                return entry.getValue();
            }
        }
        // Didn't find a peer of that socket in the list...
        Peer peer = new Peer(socket);
        peers.put(socket.getId(), peer);
        return peer;
    }

    /**
     * TODO: document
     *
     * @param id
     * @return
     */
    public static boolean peerExists(String id) {
        return peers.containsKey(id);
    }

    /**
     * TODO: document
     *
     * @param id
     * @return
     */
    public static void deletePeer(String id) {
        peers.remove(id);
    }

}
