package com.suniusoft.common.api.enums;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 微信相关API地址枚举
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/16 20:48  
 */
public enum WXApiEnums {

    ACCESS_TOKEN_URL("/cgi-bin/token","获取微信公众号access_token"),
    USER_GET("/cgi-bin/user/get","获取公众号关注者的openID"),
    GET_USER_INFO("/cgi-bin/user/info","根据openID获取用户详细信息"),
    SEND_REG_PACK("/mmpaymkttransfers/sendredpack","发送红包"),
    ENTERPRISE_TRANSFERS("/mmpaymkttransfers/promotion/transfers","企业付款"),
    SEND_MESSAGE_TO_USER("/cgi-bin/message/custom/send?access_token=","向用户发送消息"),
    WX_OUATH_API("/sns/oauth2/access_token","微信用户授权"),
    WX_OUATH_USER_INFO("/sns/userinfo","根据用户页面授权获取用户信息"),
    GET_API_TICKET("/cgi-bin/ticket/getticket","获取jsapi_ticket");



    // 成员变量
    private String code;
    private String value;

    private WXApiEnums(String code, String value) {
        this.code = code;
        this.value = value;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
