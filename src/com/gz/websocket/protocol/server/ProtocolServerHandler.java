package com.gz.websocket.protocol.server;

import org.apache.log4j.Logger;

import com.gz.websocket.msg.ProtocolMsg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class ProtocolServerHandler extends SimpleChannelInboundHandler<Object> {

	// private static final Logger log
	// =Logger.getLogger(ProtocolServerHandler.class);
	private ProtocolServerMsgHandler handler;

	public ProtocolServerHandler(ProtocolServerMsgHandler handler) {
		this.handler = handler;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
		Channel incoming = ctx.channel();

		if (obj instanceof ProtocolMsg) {
			ProtocolMsg msg = (ProtocolMsg) obj;
			msg.setChannel(incoming);
			handler.onMsgReceived(msg);
			// System.out.println("Client->Server:"+incoming.remoteAddress()+msg.getBody());
			// log.info("Client->Server:"+incoming.remoteAddress()+msg.getBody());
			// incoming.write(obj);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		handler.onSessionClosed(ctx.channel());
	}
}
