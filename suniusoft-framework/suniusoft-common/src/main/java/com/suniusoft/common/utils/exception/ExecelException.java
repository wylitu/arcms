package com.suniusoft.common.utils.exception;

/**
 *   
 *  @ProjectName: icard  
 *  @Description: execel 操作异常类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/18 13:42  
 */
public class ExecelException extends RuntimeException{

    public ExecelException() {
        super();
    }

    public ExecelException(String message) {
        super(" execel operate error,errorMsg:"+message);
    }

    public ExecelException(String message, Throwable cause) {

        super(" execel operate error,errorMsg:"+message,cause);
    }

    public ExecelException(Throwable cause) {
        super(cause);
    }
}
