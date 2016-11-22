package com.gz.websocket.protocol.client;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.gz.websocket.msg.ProtocolMsg;
import com.gz.websocket.protocol.ProtocolDecoder;
import com.gz.websocket.protocol.ProtocolEncoder;
import com.gz.websocket.protocol.ProtocolHeader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;

/**
 * 说明：自定义协议客户端
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日
 */
public class ProtocolClient {


	private static Logger log =Logger.getLogger(ProtocolClient.class);
	
	private ProtocolClientHandler protocolClientHandler;
	private ProtocolClientMsgHandler msgHandler;
	private String host;
	private int port;

	private static final int MAX_FRAME_LENGTH = 1024 * 1024;
	private static final int LENGTH_FIELD_LENGTH = 4;
	private static final int LENGTH_FIELD_OFFSET = 6;
	private static final int LENGTH_ADJUSTMENT = 0;
	private static final int INITIAL_BYTES_TO_STRIP = 0;

	public static final byte STATUS_WORKING=1;
	
	public static final byte STATUS_STOP=0;
	
	private static byte status=STATUS_STOP;
	
	private ChannelFuture f;
	
	public static byte getStatus() {
		return status;
	}

	/**
	 * 
	 */
	public ProtocolClient(String host, int port,ProtocolClientMsgHandler handler) {
		this.host = host;
		this.port = port;
		this.msgHandler =handler;
		protocolClientHandler = new ProtocolClientHandler(handler);
	}

	public void run() throws InterruptedException {

		log.info("服务启动");
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
							"decoder",
							new ProtocolDecoder(MAX_FRAME_LENGTH,
									LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
									LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
					ch.pipeline().addLast("encoder", new ProtocolEncoder());
					ch.pipeline().addLast(protocolClientHandler);

				}
			});

			// 启动客户端
			f = b.connect(host, port).sync(); // (5)
			log.info("已经连接上服务器");
//			while (true) {
//
//				// 发送消息给服务器
//				ProtocolMsg msg = new ProtocolMsg();
//				ProtocolHeader protocolHeader = new ProtocolHeader();
//				protocolHeader.setMagic((byte) 0x01);
//				protocolHeader.setMsgType((byte) 0x01);
//				protocolHeader.setReserve((short) 0);
//				protocolHeader.setSn((short) 0);
//				String body = "床前明月光疑是地上霜";
//				StringBuffer sb = new StringBuffer();
//				for (int i = 0; i < 2; i++) {
//					sb.append(body);
//				}
//
//				byte[] bodyBytes = sb.toString().getBytes(
//						Charset.forName("utf-8"));
//				int bodySize = bodyBytes.length;
//				protocolHeader.setLen(bodySize);
//
//				msg.setProtocolHeader(protocolHeader);
//				msg.setBody(sb.toString());
//
//				f.channel().writeAndFlush(msg);
//				Thread.sleep(2000);
//			}
			
			status = STATUS_WORKING;
			msgHandler.onConnect(f.channel());
//			while(true){
//				sendTestMsg();
//				try {
//					Thread.sleep(2000);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			
			// 等待连接关闭
			 f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

//	/**
//	 * @param args
//	 * @throws InterruptedException
//	 */
//	public static void main(String[] args) throws InterruptedException {
//		new ProtocolClient("localhost", 8082).run();
//	}
	public void sendTestMsg(){
		ProtocolMsg msg = new ProtocolMsg();
		ProtocolHeader protocolHeader = ProtocolHeader.defaultHeader();
		JSONObject json =new JSONObject();
		json.put("id", 111);
		json.put("name", "张三");
		json.put("money", 122234567891.78374);
		String body = json.toJSONString();

		msg.setProtocolHeader(protocolHeader);
		msg.setContent(body);

		f.channel().writeAndFlush(msg);
//		f.channel().write(msg);
	}
}
