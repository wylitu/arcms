package com.suniusoft.pay.weixin.bean.page;

public class JSConfig {
private String appId;
private String timestamp;
private String nonceStr;
private String signature;
private String url;
private String jsapiTicket;
private String mchId;


public String getTimestamp() {
	return timestamp;
}


public String getNonceStr() {
	return nonceStr;
}


public String getSignature() {
	return signature;
}


public String getUrl() {
	return url;
}


public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
}


public void setNonceStr(String nonceStr) {
	this.nonceStr = nonceStr;
}


public void setSignature(String signature) {
	this.signature = signature;
}


public void setUrl(String url) {
	this.url = url;
}


public String getJsapiTicket() {
	return jsapiTicket;
}


public void setJsapiTicket(String jsapiTicket) {
	this.jsapiTicket = jsapiTicket;
}


public String getAppId() {
	return appId;
}


public void setAppId(String appId) {
	this.appId = appId;
}




public String getMchId() {
	return mchId;
}


public void setMchId(String mchId) {
	this.mchId = mchId;
}
}
