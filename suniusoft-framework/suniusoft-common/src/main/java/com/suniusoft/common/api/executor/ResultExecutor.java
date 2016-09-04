package com.suniusoft.common.api.executor;

/**
 *  @ProjectName: sf-crm  
 *  @Description: 结果处理器
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/25 15:51  
 */
public interface ResultExecutor<T> {


    public T execute(String responseJson);
}
