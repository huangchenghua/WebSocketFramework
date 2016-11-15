package com.gz.net.protocol.client.test;


import com.gz.websocket.msg.ProtocolMsg;
import com.gz.websocket.protocol.client.ProtocolClientMsgHandler;

import io.netty.channel.Channel;

public class ProtocolClientReceivedHandler implements ProtocolClientMsgHandler {

	@Override
	public void onMsgReceived(ProtocolMsg msg) {
		

	}

	@Override
	public void onSessionClosed(Channel channel) {
		
	}

}
