package com.suniusoft.common.api.request;

import lombok.Data;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/19 14:50  
 */
@Data
public class WxRequest<T> extends ApiRequest {

    /**
     * 证书路径
     */
    private String secrtePath;


    /**
     * 商户号
     */
    private String mchId;

    /**
     * String参数
     */
    private String stringParames;

}
