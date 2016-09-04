package com.suniusoft.common.sms;

import com.suniusoft.common.sms.emay.client.SingletonClient;
import com.suniusoft.common.sms.emay.cn.b2m.eucp.sdkhttp.Mo;
import org.apache.log4j.Logger;

import java.util.List;


/**
 *   
 *  @ProjectName: sf_crm  
 *  @Description: 短信服务客户端
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/6/7 14:32  
 */
public class SMSClient {

    private static final Logger logger = Logger.getLogger(SMSClient.class);

    public static List<Mo> getMO() {

        List<Mo> moList = null;
        try {
            moList = SingletonClient.getClient().getMO();
        } catch (Exception e) {
            logger.error("调用短信接收上行信息API异常。", e);
        }
        return moList;
    }
}
