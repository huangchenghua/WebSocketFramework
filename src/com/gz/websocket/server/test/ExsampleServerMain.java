package com.gz.websocket.server.test;


import com.gz.websocket.server.WebSocketServer;


public class ExsampleServerMain {

	public static void main(String[] args) {
		GameServerMsgHandler handler=new GameServerMsgHandler();
		WebSocketServer ws=new WebSocketServer(handler);
		try {
			ws.run(18662);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
