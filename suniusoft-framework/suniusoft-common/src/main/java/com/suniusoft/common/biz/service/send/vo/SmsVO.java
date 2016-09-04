package com.suniusoft.common.biz.service.send.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/5/2 20:20  
 */
@Data
public class SmsVO {

    /**
     * 商户ID
     */
    private String sellerId;

    /**
     * 短信订购ID
     */
    private String packageId;

    /**
     * 营销标题
     */
    private String saleTitle;

    /**
     * SMS:短信,MAIL:邮件
     */
    private String sendType;

    /**
     * 营销类型
     */
    private String saleType;

    /**
     * 短信通道(短信)
     */
    private String passage;

    /**
     * 签名(短信)
     */
    private String sign;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送者地址(邮件)
     */
    private String from;

    /**
     * 发送者名称(邮件)
     */
    private String fromName;

    /**
     * 邮件标题(邮件)
     */
    private String subject;

    /**
     * 短信等级，范围1~5，数值越高优先级越高(短信)
     */
    private String smsPriority;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 短信扩展码
     */
    private String smsSerial;

    /**
     * 营销ID
     */
    private Long activityId;

    /**
     * 疲劳处理
     */
    private String fatigue;


    /**
     * 目标用户(短信、邮件)
     */
    private List<String> targetNumberList;
}
