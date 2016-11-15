package com.gz.websocket.server;

import com.gz.websocket.msg.BaseMsg;

import io.netty.channel.Channel;

public interface ServerMsgHandler {
	
	public void onMsgReceived(BaseMsg msg);
	
	public void onSessionClosed(Channel channel);
}
