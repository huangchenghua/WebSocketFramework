package com.gz.websocket.msg;

import com.gz.websocket.protocol.ProtocolHeader;


public class ProtocolMsg extends BaseMsg {
	 
	private ProtocolHeader protocolHeader = new ProtocolHeader();
 	

	/**
	 * 
	 */
	public ProtocolMsg() {
		// TODO Auto-generated constructor stub
	}
	
	public ProtocolHeader getProtocolHeader() {
		return protocolHeader;
	}

	public void setProtocolHeader(ProtocolHeader protocolHeader) {
		this.protocolHeader = protocolHeader;
	}


}
