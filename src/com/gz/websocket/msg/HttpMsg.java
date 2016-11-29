package com.gz.websocket.msg;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

public class HttpMsg extends BaseMsg{
	private JSONObject json;
	private FullHttpRequest request;
	private ChannelHandlerContext ctx;
	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public FullHttpRequest getRequest() {
		return request;
	}

	public void setRequest(FullHttpRequest request) {
		this.request = request;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	@Override
	public void parse() {
		QueryStringDecoder queryDecoder = new QueryStringDecoder(content, false);
		json=new JSONObject();
		Map<String, List<String>> uriAttributes = queryDecoder.parameters();
		for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
			for (String attrVal : attr.getValue()) {
				json.put(attr.getKey(),attrVal);
			}
		}
		
	}
}
