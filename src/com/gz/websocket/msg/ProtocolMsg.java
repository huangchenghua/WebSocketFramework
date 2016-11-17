package com.gz.websocket.msg;

import com.alibaba.fastjson.JSONObject;
import com.gz.websocket.protocol.ProtocolHeader;


public class ProtocolMsg extends BaseMsg {
	 
	private ProtocolHeader protocolHeader = new ProtocolHeader();
 	
	private JSONObject json;
	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

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

	@Override
	public void parse() {
		json=JSONObject.parseObject(content);
		mainCode = json.getIntValue("mainCode");		
	}


}
