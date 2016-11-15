package com.gz.websocket.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.HOST;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.apache.log4j.Logger;

import com.gz.websocket.msg.BaseMsg;


/**
 * WebSocketServerHandler
 *
 */
@Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger log = Logger.getLogger(WebSocketServerHandler.class);
	
	private WebSocketServerHandshaker handshaker;
	
	private ServerMsgHandler msgHandler;
	
	public WebSocketServerHandler(ServerMsgHandler handler){
		this.msgHandler = handler;
	}
	
	
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(
					String.format("%s frame types not supported", frame.getClass().getName()));
		}
		Channel channel = ctx.channel();
		String request = ((TextWebSocketFrame) frame).text();
		BaseMsg msg =new BaseMsg(channel,request);
		msgHandler.onMsgReceived(msg);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object event) throws Exception {
		if (event instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) event);
		} else if (event instanceof WebSocketFrame) {
			handleWebSocketFrame(ctx, (WebSocketFrame) event);
		}
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { 
		super.handlerRemoved(ctx);
		log.info("与客户端连接关闭"+ctx.channel().remoteAddress());
		msgHandler.onSessionClosed(ctx.channel());
	}
	
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {

		if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
			return;
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req),
				null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {

		
		if (res.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpHeaders.setContentLength(res, res.content().readableBytes());
		}
		
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		req.headers();
		if (!HttpHeaders.isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	@SuppressWarnings("deprecation")
	private static String getWebSocketLocation(FullHttpRequest req) {
		String location = req.headers().get(HOST);// + WEBSOCKET_PATH;
		return "ws://" + location;
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		try {
			log.error("客户端连接异常"+ctx.channel().remoteAddress());
			cause.printStackTrace();
			ctx.close();
		} catch (Exception e) {
			log.error(e);
		}
	}
}