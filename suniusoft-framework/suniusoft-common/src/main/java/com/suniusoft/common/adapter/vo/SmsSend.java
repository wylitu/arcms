package com.suniusoft.common.adapter.vo;

import lombok.Data;

import java.util.List;

/**
 *   
 *  @ProjectName: sf-parent  
 *  @Description: 短信发送domain
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/5/9 18:21  
 */
@Data
public class SmsSend {

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 通道, notify:通知,sale:营销
     */
    private String passage;

    /**
     * 发送任务流水
     */
    private long SeqId;

    /**
     * 短信订购ID
     */
    private String smsOrderId;

    /**
     * 短信标题
     */
    private String smsTitle;

    /**
     * 短信签名
     */
    private String smsSign;

    /**
     * 短信内容
     */
    private String smsContent;
    /**
     * 短信等级，范围1~5，数值越高优先级越高
     */
    private int smsPriority;

    /**
     * 扩展码
     */
    private String smsSerial;

    /**
     * 发送时间
     */
    private String sendTime;
    /**
     * 用户手机号
     */
    private List<String> userPhone;
}
