package com.suniusoft.pay.weixin.bean.user;

public class WeixinUserInfo {
	private String openid;
	//用户名
	private String nickname;
	//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private String unionid;
	public String getOpenid() {
		return openid;
	}
	public String getNickname() {
		return nickname;
	}
	public String getSex() {
		return sex;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	

}
