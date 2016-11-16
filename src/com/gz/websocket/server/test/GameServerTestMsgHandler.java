package com.gz.websocket.server.test;


import com.alibaba.fastjson.JSONObject;
import com.gz.websocket.msg.BaseMsg;
import com.gz.websocket.server.ServerMsgHandler;

import io.netty.channel.Channel;

public class GameServerTestMsgHandler implements ServerMsgHandler {

	
	@Override
	public void onMsgReceived(BaseMsg msg) {
		String request = msg.getContent();
		System.out.println(request);
		//JSONObject.parse(request);
		GameMsg gm = JSONObject.parseObject(request, GameMsg.class);
		System.out.println("gm:"+gm);
	}

	@Override
	public void onSessionClosed(Channel channel) {
		System.out.println("�Ͽ�����");

	}

}