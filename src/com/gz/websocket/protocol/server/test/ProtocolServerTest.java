package com.gz.websocket.protocol.server.test;

import com.gz.websocket.protocol.server.ProtocolServer;

public class ProtocolServerTest {

	public static void main(String[] args) {
		ReceivedHandler handler=new ReceivedHandler();
		int port = 18085;
		ProtocolServer server=new ProtocolServer(port, handler);
		try {
			server.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
