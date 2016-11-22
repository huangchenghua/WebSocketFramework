package com.gz.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
	private static Config loader;
	public static Map<String, String> configs = new HashMap<String, String>();
	public static synchronized Config instance(){
		if(loader== null){
			loader = new Config();
		}
		return loader;
	}
	private Config()
	{
	}
	/**
	 * 初始化常量MAP
	 */
	/**
	 * 初始化读取配置
	 */
	public void init() {
		//FileInputStream fis = null;
		try {
			//fis = new FileInputStream("conf/config.properties");
//			InputStream ins =Object. class .getResourceAsStream( "/config.properties" );
			InputStream ins =new FileInputStream( "./conf/config.properties" );
			BufferedReader in = new BufferedReader(new InputStreamReader(ins, "iso-8859-1"));
			Properties pro = new Properties();
			pro.load(in);
			Enumeration keys = pro.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = pro.getProperty(key);
				configs.put(key, value);
			}
			System.out.println("成功加载常量");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得某个常量的值(字符串类型)
	 * 
	 * @param key
	 * @return
	 */
	public String getSValue(String key) {
		return configs.get(key);
	}

	/**
	 * 获得某个常量的值(long类型)
	 * 
	 * @param key
	 * @return
	 */
	public long getLValue(String key) {
		return Long.parseLong(configs.get(key));
	}

	/**
	 * 获得某个常量的值(int类型)
	 * 
	 * @param key
	 * @return
	 */
	public int getIValue(String key) {
		return Integer.parseInt(configs.get(key));
	}

	/**
	 * 获得浮点型的值(float类型)
	 * 
	 * @param key
	 * @return
	 */
	public float getFValue(String key) {
		return Float.parseFloat(configs.get(key));
	};

	/**
	 * 添加变量
	 * 
	 * @param key
	 * @param value
	 */
	public static void putValue(String key, String value) {
		configs.put(key, value);
	}
}
