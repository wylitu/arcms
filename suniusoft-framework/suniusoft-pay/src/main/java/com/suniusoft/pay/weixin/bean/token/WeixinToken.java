package com.suniusoft.pay.weixin.bean.token;


public class WeixinToken {
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;
	//过期时间，单位：秒
	private long expirationTime;
	
	private String ticket;
	public String getToken() {
		return token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public long getExpirationTime() {
		return expirationTime;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	


}