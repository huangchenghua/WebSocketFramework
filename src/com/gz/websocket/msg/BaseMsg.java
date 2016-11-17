package com.gz.websocket.msg;

import io.netty.channel.Channel;

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
}
