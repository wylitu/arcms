package com.suniusoft.common.api.request;

import lombok.Data;

import java.util.Map;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 请求基础类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/16 18:44  
 */
@Data
public class ApiRequest {

    /**
     * api接口
     */
    private String api;

    /**
     * 接口基础url
     */
    private String url;

    /**
     * 带请求头User-Agent
     */
    protected Map<String,String> headerMap;


    /**
     * 页码
     */
    private Integer pageNo=1;

    /**
     * 每页大小
     */
    private Integer pageSize=40;


    /**
     * 其他参数
     */
    private Map<String, String> parames;

    /**
     * json请求参数
     */
    private String jsonStrParam;


}
