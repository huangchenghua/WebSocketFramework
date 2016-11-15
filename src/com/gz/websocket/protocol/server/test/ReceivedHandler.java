package com.gz.websocket.protocol.server.test;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.gz.websocket.msg.ProtocolMsg;
import com.gz.websocket.protocol.server.ProtocolServerMsgHandler;

import io.netty.channel.Channel;

public class ReceivedHandler implements ProtocolServerMsgHandler {
	private static final Logger log =Logger.getLogger(ReceivedHandler.class);
	@Override
	public void onMsgReceived(ProtocolMsg msg) {
		log.info(msg.getContent());
		JSONObject json=JSONObject.parseObject(msg.getContent());
		long money=json.getLongValue("money");
		log.info(money);
	}

	@Override
	public void onSessionClosed(Channel channel) {
		

	}

}
