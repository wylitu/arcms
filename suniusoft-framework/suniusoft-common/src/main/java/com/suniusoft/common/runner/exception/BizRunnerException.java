package com.suniusoft.common.runner.exception;

/**
 *   
 *  @ProjectName: icard  
 *  @Description: <p>
 *                 异步执行器异常类
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/6  
 */
public class BizRunnerException extends RuntimeException{
    public BizRunnerException() {
        super();
    }

    public BizRunnerException(String message) {
        super(message);
    }

    public BizRunnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizRunnerException(Throwable cause) {
        super(cause);
    }

    protected BizRunnerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
