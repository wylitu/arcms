package com.suniusoft.pay.weixin.message.req;

public class BaseMessage {
	// 开发者微信号
	private String ToUserNo;
	// 发送方帐号（一个OpenID）
	private String FromUserName;
	// 消息创建时间 （整型）
	private long CreateTime;
	// 消息类型（text/image/location/link）
	private String MsgType;
	// 消息id，64位整型
	private long MsgId;

	public String getToUserNo() {
		return ToUserNo;
	}

	public void setToUserNo(String toUserNo) {
		ToUserNo = toUserNo;
	}

	public String getFromUserNo() {
		return FromUserName;
	}

	public void setFromUserNo(String fromUserNo) {
		FromUserName = fromUserNo;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public long getMsgId() {
		return MsgId;
	}

	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
}
