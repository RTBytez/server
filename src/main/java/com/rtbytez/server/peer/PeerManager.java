package com.rtbytez.server.peer;

import com.corundumstudio.socketio.SocketIOClient;
import com.rtbytez.server.util.Console;

import java.util.HashMap;
import java.util.Map;

public class PeerManager {

    private static final HashMap<String, Peer> peers = new HashMap<>();

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private PeerManager() {

    }

    /**
     * Retrieve a Peer based on a socket, if one doesn't exist, create one.
     *
     * @param socket Socket to get peer from or create Peer from socket
     * @return Newly created or existing peer
     */
    public static Peer getPeer(SocketIOClient socket) {
        for (Map.Entry<String, Peer> entry : peers.entrySet()) {
            if (entry.getKey().equals(socket.getSessionId().toString())) {
                return entry.getValue();
            }
        }
        // Didn't find a peer of that socket in the list...
        Peer peer = new Peer(socket);
        peers.put(peer.getId(), peer);
        return peer;
    }

    /**
     * Retrieve a Peer based on it's ID.
     * NOTE: This is NOT Socket ID!
     *
     * @param id ID of Peer
     * @return Peer instance that is associated to ID
     * @see PeerManager
     */
    public static Peer getPeer(String id) {
        Peer peer = peers.get(id);
        if (peer == null) {
            Console.error("PeerManager", "Returned null peer on getPeer: PeerID=" + id);
        }
        return peer;
    }


    /**
     * Retrieve a Peer based on it's socket ID
     */
    public static Peer getPeerBySocketId(String id) {
        for (Map.Entry<String, Peer> stringPeerEntry : peers.entrySet()) {
            if (stringPeerEntry.getValue().getSocketId().equals(id)) {
                return stringPeerEntry.getValue();
            }
        }
        Console.error("PeerManager", "Returned null peer on getPeerBySocketId: PeerSocketId=" + id);
        return null;
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
