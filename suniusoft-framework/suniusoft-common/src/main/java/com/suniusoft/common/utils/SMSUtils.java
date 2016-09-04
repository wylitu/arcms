package com.suniusoft.common.utils;

import com.google.common.collect.Maps;
import com.suniusoft.common.adapter.SmsAdapter;
import com.suniusoft.common.adapter.vo.SmsSend;
import com.suniusoft.common.biz.service.send.vo.SmsVO;
import com.suniusoft.common.constant.Constant;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 165短信接口
 *  @author litu  litu@shufensoft.com
 *  @date 15/12/17  
 */
public class SMSUtils {


    private static final Logger logger = Logger.getLogger(SMSUtils.class);

    public static String sendSmsCode(String mobile, String code){

        String api = PropertyReader.getValue("sms.api.url");

        String account = PropertyReader.getValue("sms.account");

        String passwd = PropertyReader.getValue("sms.password");

        Map<String,String> param = Maps.newHashMap();
        param.put("userid","");
        param.put("mobile",mobile);
        param.put("action","send");
        param.put("account",account);
        param.put("password", Md5Encrypt.mmd5(passwd));
        param.put("content",code);
        param.put("sendTime",null);
        param.put("extno",null);

        return HttpUtils.doPost(api,param);

    }


    /**
     * 发送及时短信
     *
     * @param saleType   营销类型
     * @param type       发送类型，营销:sale、通知:notify
     * @param mobile     手机号码
     * @param smsContent 短信内容
     * @param isFatigue  是否需要疲劳校验 1：需要 0：不需要
     * @return
     */
    public static String sendSms(String saleType, String type, String mobile, String smsContent, String isFatigue) {

        String reCode = "-1";

        String seqId = String.valueOf(IdGenUtils.idGen());

        try {

            SmsVO smsVO = new SmsVO();

            smsVO.setSmsPriority("5");
            smsVO.setSendType("SMS");
            smsVO.setFatigue(isFatigue);


            List<String> mobileList = new ArrayList<String>();
            mobileList.add(mobile);
            smsVO.setSaleTitle(saleType);
            smsVO.setSaleType(saleType);
            smsVO.setPassage(type);
            smsVO.setContent(smsContent);
            smsVO.setTargetNumberList(mobileList);

            SmsSend smsSend = new SmsSend();
            smsSend.setSmsContent(smsVO.getContent());
            smsSend.setSmsPriority(Integer.valueOf(smsVO.getSmsPriority()));
            smsSend.setUserPhone(smsVO.getTargetNumberList());
            smsSend.setSeqId(Long.valueOf(seqId));
            smsSend.setPassage(smsVO.getPassage());
            smsSend.setSmsSerial(smsVO.getSmsSerial());

            int code = SmsAdapter.smsSend(smsSend);

            if (0 == code) {

                reCode = "0";

                logger.info("seqId:" + seqId + "成功向" + smsVO.getTargetNumberList().get(0) + "发送一条验证码。");

            }

        } catch (Exception e) {

            logger.error("SendService.tdStrsendSms() is error.");
        }

        return reCode;
    }

    /**
     * 发送短信验证码
     *
     * @param mobile     手机号码
     * @param smsContent 短信内容
     * @return -1:失败,0:成功
     * @smsSign 签名
     */
    public static String sendSmsVerifyCode(String mobile, String smsContent) {

        return sendSms(Constant.SmsSaleType.SMS_VERIFY, Constant.SmsSend.SEND_NOTIFY, mobile, smsContent, Constant.SmsFatigueFlag.IS_NOT_FATIGUE);

    }

    public static void main(String[] args) {
        sendSmsCode("15868890378", "12342");
    }


}
