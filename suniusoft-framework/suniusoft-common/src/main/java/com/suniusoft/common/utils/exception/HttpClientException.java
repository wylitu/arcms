package com.suniusoft.common.utils.exception;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: httpclient异常类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/13 14:02  
 */
public class HttpClientException extends RuntimeException {

    public HttpClientException() {
        super();
    }

    public HttpClientException(String message) {
        super("http request error,errorMsg:"+message);
    }

    public HttpClientException(String message, Throwable cause) {

        super("http request error,errorMsg:"+message,cause);
    }

    public HttpClientException(Throwable cause) {
        super(cause);
    }
}
