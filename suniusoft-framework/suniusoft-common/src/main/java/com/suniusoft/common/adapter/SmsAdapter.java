package com.suniusoft.common.adapter;

import com.suniusoft.common.adapter.vo.SmsSend;
import com.suniusoft.common.sms.emay.client.SingletonClient;
import com.suniusoft.common.sms.emay.cn.b2m.eucp.sdkhttp.Mo;
import com.suniusoft.common.sms.emay.cn.b2m.eucp.sdkhttp.StatusReport;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.List;


/**
 *   
 *  @ProjectName: sf-parent  
 *  @Description: 
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/5/9 17:55  
 */
public class SmsAdapter {

    private static final Logger logger = Logger.getLogger(SmsAdapter.class);

    public static int smsSend(SmsSend smsSend) {
        int size = smsSend.getUserPhone().size();
        String[] userPhone = (String[]) smsSend.getUserPhone().toArray(new String[size]);
        int resultCode = -1;

        try {

            resultCode = SingletonClient.getClient("notify".equals(smsSend.getPassage()) ? "notify" : "sale").sendSMSEx("", userPhone, smsSend.getSmsContent(), smsSend.getSmsSerial(), "GBK", smsSend.getSmsPriority(), smsSend.getSeqId());
            logger.info("短信API发送成功：" + resultCode);
        } catch (RemoteException e) {
            logger.error("调用短信API异常", e);
        }
        //return 0;
        return resultCode;
    }

    /**
     * 获取短信发送状态结果
     *
     * @return
     */
    public static List<StatusReport> getSmsStatusReport(String type) {


        List<StatusReport> statusReportList = null;
        try {
            statusReportList = SingletonClient.getClient("notify".equals(type) ? "notify" : "sale").getReport();
        } catch (RemoteException e) {
            logger.error("调用短信状态接收API异常", e);
        }
        return statusReportList;
    }

    /**
     * 获取短信下行信息
     *
     * @return
     */
    public static List<Mo> getMO(String type) {


        List<Mo> moList = null;

        try {
            moList = SingletonClient.getClient("notify".equals(type) ? "notify" : "sale").getMO();
        } catch (Exception e) {
            logger.error("调用短信接收上行信息API异常。", e);
        }
        return moList;
    }
}

