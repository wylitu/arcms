package com.suniusoft.common.api.exception;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 微信接口调用异常类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/18 13:42  
 */
public class WxApiException extends ApiException{

    private static String client = "weixin";

    public WxApiException() {
        super();
    }

    public WxApiException(String message) {
        super(client, message);
    }

    public WxApiException(String api, String message) {
        super(client, api, message);
    }

    public WxApiException( String api, String errorCode, String message) {
        super(client, api, errorCode, message);
    }

    public WxApiException( String message, Throwable cause) {
        super(client, message, cause);
    }

    public WxApiException( String api, String errorCode, String message, Throwable cause) {
        super(client, api, errorCode, message, cause);
    }

    public WxApiException( String api, String message, Throwable cause) {
        super(client, api, message, cause);
    }



    public WxApiException(Throwable cause) {
        super(cause);
    }
}
