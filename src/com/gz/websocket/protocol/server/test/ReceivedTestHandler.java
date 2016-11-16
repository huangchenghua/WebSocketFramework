package com.gz.websocket.protocol.server.test;

import com.gz.websocket.msg.ProtocolMsg;
import com.gz.websocket.protocol.server.ProtocolServerMsgHandler;

import io.netty.channel.Channel;

public class ReceivedTestHandler implements ProtocolServerMsgHandler {

	@Override
	public void onMsgReceived(ProtocolMsg msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSessionClosed(Channel channel) {
		// TODO Auto-generated method stub
		
	}

}
