package com.suniusoft.common.adapter.vo;

import lombok.Data;

/**
 *   
 *  @ProjectName: sf_crm  
 *  @Description: 
 *  @author zoujian  zoujian@suniusoft.com
 *  @date 15/8/2 上午11:38  
 */
@Data
public class TicketVO {

    private String jsapi;

    private String errmsg;

    private String ticket;

    private String expires_in;

    private int expiresTime;
}
