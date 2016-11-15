package com.gz.websocket.server.test;

import java.io.Serializable;

public class GameMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6811633448420444692L;
	protected int par1;
	protected String par2;
	protected Object data;
	protected long timeStamp;
	@Override
	public String toString() {
		return "Event [par1=" + par1 + ", par2=" + par2+ ", data=" + data + ", timeStamp=" + timeStamp + "]";
	}
	public int getPar1() {
		return par1;
	}
	public void setPar1(int par1) {
		this.par1 = par1;
	}
	public String getPar2() {
		return par2;
	}
	public void setPar2(String par2) {
		this.par2 = par2;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
