package com.suniusoft.common.api.exception;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: <p>
 *                api异常类
 *               </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/8  
 */
public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String client,String message) {
        super(client+" api execute error,errorMsg:"+message);
    }

    public ApiException(String client,String api,String message) {
        super(client+"[api:"+(api==null?"":api)+" execute error,errorMsg:"+message);
    }

    public ApiException(String client,String api,String errorCode,String message) {

        super(client+" api execute error,errorMsg:"+message+"[api:"+(api==null?"":api)+";errorCode:"
                +(errorCode==null?"":errorCode)+"]");
    }

    public ApiException(String client, String message,Throwable cause) {

        super(client+" execute error,errorMsg:"+message,cause);
    }

    public ApiException(String client,String api,String errorCode,String message,Throwable cause) {

        super(client+" api execute error,errorMsg:"+message+"[api:"+(api==null?"":api)+";errorCode:"
                +(errorCode==null?"":errorCode)+"]",cause);

    }

    public ApiException(String client,String api,String message,Throwable cause) {

        super(client+" api execute error,errorMsg:"+message+"[api:"+(api==null?"":api)+"]",cause);

    }


    public ApiException(Throwable cause) {
        super(cause);
    }
}
