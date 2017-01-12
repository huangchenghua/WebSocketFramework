package com.gz.websocket.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gz.websocket.msg.BaseMsg;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ClientMsg extends BaseMsg {
	private JSONObject json;
	
	
	public JSONObject getJson() {
		return json;
	}


	public void setJson(JSONObject json) {
		this.json = json;
	}


	public void parse(){
		json = JSONObject.parseObject(content);
		mainCode = json.getIntValue("mainCode");
	}
	
	public ClientMsg(BaseMsg msg){
		this.channel =msg.getChannel();
		this.content = msg.getContent();
		
	}
	
	public ClientMsg(){
		super();
		json = new JSONObject();
	}
	
	@Override
	public void clear() {
		super.clear();
		json = new JSONObject();
	}
	
	public void sendSelf(){
		if(channel!=null && channel.isActive())
			channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(json)));
		System.out.println("发包："+json.toJSONString());
	}
	
	public void put(String key,Object value){
		json.put(key, value);
	}
	
	public ClientMsg copy(){
		ClientMsg msg=new ClientMsg();
		msg.setJson(json);
		return msg;
	}
}
