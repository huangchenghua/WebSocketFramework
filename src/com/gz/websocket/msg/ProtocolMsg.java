package com.gz.websocket.msg;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;
import com.gz.websocket.protocol.ProtocolHeader;


public class ProtocolMsg extends BaseMsg {
	 
	private ProtocolHeader protocolHeader = ProtocolHeader.defaultHeader();
 	
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
		json = new JSONObject();
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

	@Override
	public void setContent(String content) {
		super.setContent(content);
		StringBuffer sb = new StringBuffer(content);
		byte[] bodyBytes = sb.toString().getBytes(
				Charset.forName("utf-8"));
		int bodySize = bodyBytes.length;
		protocolHeader.setLen(bodySize);
	}
	
	@Override
	public void sendSelf() {
		if(channel!=null && channel.isActive()){
			refreshContent();
			channel.writeAndFlush(this);
		}
	}
	
	@Override
	public void clear() {
		super.clear();
		json = new JSONObject();
	}
	
	public void refreshContent(){
		if(json!=null)
			setContent(json.toJSONString());
	}
	
	public void put(String key,Object value){
		json.put(key, value);
	}
}
