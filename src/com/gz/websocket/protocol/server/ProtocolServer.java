/**
 * 
 */
package com.gz.websocket.protocol.server;

import com.gz.websocket.protocol.ProtocolDecoder;
import com.gz.websocket.protocol.ProtocolEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class ProtocolServer {

	
	private static final int MAX_FRAME_LENGTH = 1024 * 1024;
	private static final int LENGTH_FIELD_LENGTH = 4;
	private static final int LENGTH_FIELD_OFFSET = 6;
	private static final int LENGTH_ADJUSTMENT = 0;
	private static final int INITIAL_BYTES_TO_STRIP = 0;
	private ProtocolServerHandler protocolServerHandler;
	/**
	 * 
	 */
	public ProtocolServer(ProtocolServerMsgHandler handler) {
		protocolServerHandler = new ProtocolServerHandler(handler);
	}

	 public void run(int port) throws Exception {
	        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap(); // (2)
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class) // (3)
	             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
	                 @Override
	                 public void initChannel(SocketChannel ch) throws Exception {
	 					ch.pipeline().addLast("decoder",
								new ProtocolDecoder(MAX_FRAME_LENGTH,
										LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH, 
										LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
	                     ch.pipeline().addLast("encoder", new ProtocolEncoder());
	                     ch.pipeline().addLast(protocolServerHandler);
	                 }
	             })
	             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
	             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

	            ChannelFuture f = b.bind(port).sync(); // (7)
	            
	    		System.out.println("Server start listen at " + port );
	    		
	            f.channel().closeFuture().sync();
	            

	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
	    }
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8082;
//        }
//        new ProtocolServer(port).run();
	}

}
