package com.rtbytez.server.events.io;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.rtbytez.common.util.Console;
import com.rtbytez.server.peer.PeerManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class ExceptionEvent implements ExceptionListener {
    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        Console.log("ExceptionHandler", PeerManager.getPeer(client).getShort() + " - " + e.getMessage());
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        Console.log("ExceptionHandler", PeerManager.getPeer(client).getShort() + " - " + e.getMessage());
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        Console.log("ExceptionHandler", PeerManager.getPeer(client).getShort() + " - " + e.getMessage());
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        Console.log("ExceptionHandler", PeerManager.getPeer(client).getShort() + " - " + e.getMessage());
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        return true;
    }
}
