package com.gz.websocket.msg;

import io.netty.channel.Channel;

public class BaseMsg {
	protected Channel channel;
	protected String content;
	
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
	
}
