package com.suniusoft.common.api.response;

import lombok.Getter;
import lombok.Setter;

/**
 *  @ProjectName: sf-crm  
 *  @Description: 基础响应类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/16 22:39  
 */

public class ApiResponse {

    /**
     * 错误码
     */
    @Setter
    @Getter
    private String errorCode;

    /**
     * 错误消息
     */
    @Setter
    @Getter
    private String errorMessage;

    /**
     * 返回json结果
     */
    @Setter
    @Getter
    private String resultJson;

    /**
     * 分页查询记录总数
     */
    @Setter
    @Getter
    private Integer totalResults;

    /**
     * 是否存在下一页
     */
    @Setter
    @Getter
    private Boolean hashNext;

}
