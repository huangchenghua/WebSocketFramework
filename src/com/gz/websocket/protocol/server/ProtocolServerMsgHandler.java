package com.gz.websocket.protocol.server;

import com.gz.websocket.msg.ProtocolMsg;

import io.netty.channel.Channel;

public interface ProtocolServerMsgHandler {

	public void onMsgReceived(ProtocolMsg msg);
	
	public void onSessionClosed(Channel channel);
	
}
