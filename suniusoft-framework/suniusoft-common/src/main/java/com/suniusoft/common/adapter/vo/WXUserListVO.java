package com.suniusoft.common.adapter.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/15 17:45  
 */
@Data
public class WXUserListVO {
    /**
     * 关注该公众帐号的总用户数
     */
    private Long total;

    /**
     * 每次拉取用户数
     */
    private Long count;
    /**
     * 列表数据
     */
    private List<String> userOpenIdList = new ArrayList<String>();

    /**
     *  拉取列表的后一个用户的OPENID
     */
    private String next_openid;

}
