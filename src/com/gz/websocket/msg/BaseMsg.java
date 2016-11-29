package com.gz.websocket.msg;

import com.alibaba.fastjson.JSON;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class BaseMsg {
	protected Channel channel;
	protected String content;
	protected int mainCode;

	public BaseMsg(){}
	
	public BaseMsg(Channel channel, String content) {
		super();
		this.channel = channel;
		this.content = content;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getMainCode() {
		return mainCode;
	}

	public void setMainCode(int mainCode) {
		this.mainCode = mainCode;
	}
	
	public void parse()
	{
		
	}
	
	public void sendSelf(){
		channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(content)));
	}
	
	public void clear(){
		content = null;
		mainCode = 0;
	}
	
	public void closeChannel(){
		try {
			if(channel!=null)
				channel.close();
		} catch (Exception e) {
		}
	}
}
