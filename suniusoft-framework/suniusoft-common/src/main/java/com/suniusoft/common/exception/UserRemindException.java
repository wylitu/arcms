package com.suniusoft.common.exception;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: 友好提示用户的异常类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/24 22:42  
 */
public class UserRemindException extends RuntimeException{

    public UserRemindException() {
        super();
    }

    public UserRemindException(String message) {
        super(message);
    }

    public UserRemindException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRemindException(Throwable cause) {
        super(cause);
    }

    protected UserRemindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
