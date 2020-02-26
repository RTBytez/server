package com.rtbytez.server.peer;

import io.socket.socketio.server.SocketIoSocket;

import java.util.HashMap;
import java.util.Map;

public class PeerManager {

    private static HashMap<String, Peer> peers = new HashMap<>();

    /**
     * Retrieve a Peer based on a socket, if one doesn't exist, create one.
     *
     * @param socket Socket to get peer from or create Peer from socket
     * @return Newly created or existing peer
     */
    public static Peer getPeer(SocketIoSocket socket) {
        for (Map.Entry<String, Peer> entry : peers.entrySet()) {
            if (entry.getKey().equals(socket.getId())) {
                return entry.getValue();
            }
        }
        // Didn't find a peer of that socket in the list...
        Peer peer = new Peer(socket);
        peers.put(peer.getId(), peer);
        return peer;
    }

    /**
     * Retrieve a Peer based on it's ID
     *
     * @param id ID of Peer
     * @return Peer instance that is associated to ID
     */
    public static Peer getPeer(String id) {
        return peers.get(id);
    }

    /**
     * Check if peer exists based on ID
     *
     * @param id ID in question
     * @return boolean if peer exists or not
     */
    public static boolean peerExists(String id) {
        return peers.containsKey(id);
    }

    /**
     * Delete a peer from server memory
     *
     * @param id ID of peer to delete
     */
    public static void deletePeer(String id) {
        peers.remove(id);
    }

}
