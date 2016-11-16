package com.gz.websocket.protocol.server.test;

import com.gz.websocket.protocol.server.ProtocolServer;

public class ProtocolServerTest {

	public static void main(String[] args) {
		ReceivedTestHandler handler=new ReceivedTestHandler();
		int port = 18085;
		ProtocolServer server=new ProtocolServer(handler);
		try {
			server.run(port);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
