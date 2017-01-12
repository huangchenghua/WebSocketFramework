package com.gz.http;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import io.netty.util.AsciiString;

public class HttpDecoderAndEncoder {
	private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
	private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
	private static final AsciiString CONNECTION = new AsciiString("Connection");
	private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");
	
	private static final AsciiString Access_Control_Allow_Origin=new AsciiString("Access-Control-Allow-Origin");
	/**
	 * 请求解析
	 * @param ctx
	 * @param req
	 */
	public static void Request(ChannelHandlerContext ctx, FullHttpRequest req){
		
		
	}
	
	/**
	 * 解析请求参数
	 * @param request
	 * @return
	 */
	public static JSONObject getRequestParameters(FullHttpRequest request){
		String jsonStr = request.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
		QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
		JSONObject json=new JSONObject();
		Map<String, List<String>> uriAttributes = queryDecoder.parameters();
		for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
			for (String attrVal : attr.getValue()) {
				json.put(attr.getKey(),attrVal);
			}
		}
		return json;
	}
	
	/**
	 * 响应Http请求
	 * @param ctx
	 * @param req
	 * @param msg
	 */
	public static void Response(ChannelHandlerContext ctx, FullHttpRequest req,String msg){
	 Response( ctx,  req, msg,OK);
	}
	/**
	 * 响应Http请求
	 * @param ctx
	 * @param req
	 * @param res
	 */
	public static void Response(ChannelHandlerContext ctx, FullHttpRequest req,String msg,HttpResponseStatus status) {
//		if (HttpUtil.is100ContinueExpected(req)) {
//			ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
//		}
		try {
//			byte[] res=msg.getBytes("UTF-8");
//			boolean keepAlive = HttpUtil.isKeepAlive(req);
//			
//			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.wrappedBuffer(res));
//			response.headers().set(Access_Control_Allow_Origin, "*");
//			response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
//			response.headers().set(CONTENT_TYPE,"text/plain; charset=UTF-8");
//			if (!keepAlive) {
//				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//			} else {
//				response.headers().set(CONNECTION, KEEP_ALIVE);
//				ctx.write(response);
//			}
			
			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH,
                    response.content().readableBytes());
            response.headers().set("Access-Control-Allow-Origin","*");
            if (HttpHeaders.isKeepAlive(req)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
