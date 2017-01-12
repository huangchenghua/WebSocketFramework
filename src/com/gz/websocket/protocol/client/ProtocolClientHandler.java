package com.gz.websocket.protocol.client;
import org.apache.log4j.Logger;

import com.gz.websocket.msg.ProtocolMsg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 说明：处理器
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月7日 
 */
@Sharable
public class ProtocolClientHandler extends SimpleChannelInboundHandler<Object> {
	
	private static final Logger log = Logger.getLogger(ProtocolClientHandler.class);
	private ProtocolClientMsgHandler handler;

	public ProtocolClientHandler(ProtocolClientMsgHandler handler) {
		this.handler = handler;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj)
			throws Exception {
        Channel incoming = ctx.channel();
		//System.out.println("Server->Client:"+incoming.remoteAddress()+obj.toString());
		log.info("Server->Client:"+incoming.remoteAddress()+obj.toString());
		if(obj instanceof ProtocolMsg) {
			ProtocolMsg msg = (ProtocolMsg)obj;
			msg.setChannel(incoming);
			handler.onMsgReceived(msg);
		}
		
	}
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        
    }

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		handler.onSessionClosed(ctx.channel());
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		handler.onExceptionCaught(ctx.channel(),cause);
	}
}
