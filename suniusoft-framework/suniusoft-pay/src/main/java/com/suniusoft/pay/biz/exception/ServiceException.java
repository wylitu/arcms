package com.suniusoft.pay.biz.exception;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: 业务层异常类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/24 22:42  
 */
public class ServiceException extends RuntimeException{

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(" service execute error,errorMsg:"+message);
    }

    public ServiceException(String method, String message) {

        super(" service method "+(method==null?"":method)+" execute error,errorMsg:"+message);
    }

    public ServiceException(String method, Throwable cause) {

        super( "service method "+(method==null?"":method)+" execute error",cause);
    }

    public ServiceException(String method, String message, Throwable cause) {

        super(" service method "+(method==null?"":method)+" execute error,errorMsg:"+message,cause);

    }


    public ServiceException(Throwable cause) {
        super(cause);
    }
}
