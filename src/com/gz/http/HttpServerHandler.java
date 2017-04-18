package com.gz.http;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandler.Sharable;


@Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private HashMap<String, HttpServerMsgHandler> httphandlersChain=new HashMap<>();
	
	
	public HttpServerHandler(){
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
//		String path=request.uri();
//		HttpServerMsgHandler handler = httphandlersChain.get(path);
//		if(handler!=null){
//			if (request.method().equals(HttpMethod.GET)) {
//				handler.doGet(ctx, request);
//			} else if (request.method().equals(HttpMethod.POST)||request.method().equals(HttpMethod.OPTIONS)) {
//				handler.doPost(ctx, request);
//			}
//		}
//		else
//			ctx.close();
		
		URI uri = new URI(request.uri());
//      System.err.println("request uri==" + uri.getPath());
		String path=uri.getPath();
		HttpServerMsgHandler handler = httphandlersChain.get(path);
		Map<String, String> parmMap = HttpDecoderAndEncoder.parse(request);
		if(handler!=null){
			if (request.method().equals(HttpMethod.GET)) {
				handler.doGet(ctx, request, parmMap);
			} else if (request.method().equals(HttpMethod.POST)||request.method().equals(HttpMethod.OPTIONS)) {
				handler.doPost(ctx, request, parmMap);
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
