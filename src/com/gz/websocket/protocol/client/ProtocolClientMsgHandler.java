package com.gz.websocket.protocol.client;


import com.gz.websocket.msg.ProtocolMsg;

import io.netty.channel.Channel;

public interface ProtocolClientMsgHandler {
	public void onMsgReceived(ProtocolMsg msg);
	
	public void onSessionClosed(Channel channel);
}
