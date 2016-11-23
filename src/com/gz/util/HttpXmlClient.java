package com.gz.util;

import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;  
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.ParseException;  
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.client.methods.HttpUriRequest;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.protocol.HTTP;  
import org.apache.http.util.EntityUtils;  
import org.apache.log4j.Logger;  
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.TrustManager;  
import javax.net.ssl.X509TrustManager;  
import org.apache.http.conn.ClientConnectionManager;  
import org.apache.http.conn.scheme.Scheme;  
import org.apache.http.conn.scheme.SchemeRegistry;  
import org.apache.http.conn.ssl.SSLSocketFactory;  
import org.apache.http.impl.client.DefaultHttpClient;
  
public class HttpXmlClient {  
//    private static Logger log = Logger.getLogger(HttpXmlClient.class);  
      
    public static String post(String url, Map<String, String> params) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
          
//        log.info("create httppost:" + url);  
        HttpPost post = postForm(url, params);  
          
        body = invoke(httpclient, post);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
      
    public static String get(String url) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
          
//        log.info("create httppost:" + url);  
        HttpGet get = new HttpGet(url);  
        body = invoke(httpclient, get);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
          
      
    private static String invoke(DefaultHttpClient httpclient,  
            HttpUriRequest httpost) {  
          
        HttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);  
          
        return body;  
    }  
  
    private static String paseResponse(HttpResponse response) {  
//        log.info("get response from http server..");  
        HttpEntity entity = response.getEntity();  
          
//        log.info("response status: " + response.getStatusLine());  
        String charset = EntityUtils.getContentCharSet(entity);  
//        log.info(charset);  
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
//            log.info(body);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return body;  
    }  
  
    private static HttpResponse sendRequest(DefaultHttpClient httpclient,  
            HttpUriRequest httpost) {  
//        log.info("execute post...");  
        HttpResponse response = null;  
          
        try {  
            response = httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return response;  
    }  
  
    private static HttpPost postForm(String url, Map<String, String> params){  
          
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }  
          
        try {  
//            log.info("set utf-8 form entity to httppost");  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
          
        return httpost;  
    }  
    
    public static  String doPost(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    } 
    
    
}  
class SSLClient extends DefaultHttpClient{  
    public SSLClient() throws Exception{  
        super();  
        SSLContext ctx = SSLContext.getInstance("TLS");  
        X509TrustManager tm = new X509TrustManager() {  
                @Override  
                public void checkClientTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException {  
                }  
                @Override  
                public void checkServerTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException {  
                }  
                @Override  
                public X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
        };  
        ctx.init(null, new TrustManager[]{tm}, null);  
        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
        ClientConnectionManager ccm = this.getConnectionManager();  
        SchemeRegistry sr = ccm.getSchemeRegistry();  
        sr.register(new Scheme("https", 443, ssf));  
    }  
}  