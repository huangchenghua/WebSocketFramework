package com.gz.http;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;

import io.netty.channel.ChannelHandler.Sharable;


@Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private HashMap<String, HttpServerMsgHandler> httphandlersChain=new HashMap<>();
	
	
	public HttpServerHandler(){
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		String path=request.uri();
		HttpServerMsgHandler handler = httphandlersChain.get(path);
		if(handler!=null){
			if (request.method().equals(HttpMethod.GET)) {
				handler.doGet(ctx, request);
			} else if (request.method().equals(HttpMethod.POST)||request.method().equals(HttpMethod.OPTIONS)) {
				handler.doPost(ctx, request);
			}
		}
		else
			ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}

	public void addHandler(HttpServerMsgHandler handler){
		httphandlersChain.put(handler.getPath(), handler);
	}
	
}
