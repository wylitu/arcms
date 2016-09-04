package com.suniusoft.pay.weixin.message.resp;

public class BaseMessage {
	// 接收方帐号（收到的OpenID）
	private String ToUserNo;
	// 开发者微信号
	private String FromUserName;
	// 消息创建时间 （整型）
	private long CreateTime;
	// 消息类型（text/music/news）
	private String MsgType;
	// 位0x0001被标志时，星标刚收到的消息
	private int FuncFlag;

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

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
}
