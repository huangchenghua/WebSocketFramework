package com.gz.websocket.protocol.client.test;

import com.gz.websocket.protocol.client.ProtocolClient;

public class ProtocolClientTest {

	public static void main(String[] args) {
		
		final ProtocolClient client=new ProtocolClient("127.0.0.1", 18085, new ProtocolClientReceivedHandler());
		
//		try {
//			client.run();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		Thread t=new Thread(){
			@Override
			public void run() {
				try {
					client.run();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();

		while(true){
			try {
				if(client.getStatus() == ProtocolClient.STATUS_WORKING){
					client.sendTestMsg();
				}
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
