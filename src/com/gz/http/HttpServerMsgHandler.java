package com.gz.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpServerMsgHandler {
	
	public String getPath();
	
	public void doPost(ChannelHandlerContext ctx, FullHttpRequest request);
	
	public void doGet(ChannelHandlerContext ctx, FullHttpRequest request);
}
