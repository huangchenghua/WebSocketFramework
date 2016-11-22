package com.gz.websocket.protocol.server.test;

import com.alibaba.fastjson.JSONObject;
import com.gz.websocket.msg.ProtocolMsg;
import com.gz.websocket.protocol.server.ProtocolServerMsgHandler;

import io.netty.channel.Channel;

public class ReceivedTestHandler implements ProtocolServerMsgHandler {

	@Override
	public void onMsgReceived(ProtocolMsg msg) {
		JSONObject json = msg.getJson();
		int serverId = json.getIntValue("");
		
	}

	@Override
	public void onSessionClosed(Channel channel) {
		// TODO Auto-generated method stub
		
	}

}
