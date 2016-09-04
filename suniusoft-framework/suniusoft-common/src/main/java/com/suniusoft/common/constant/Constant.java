package com.suniusoft.common.constant;

/**
 *   
 *  @ProjectName: procurement 
 *  @Description:
 *  @author dadi  litu@51xianqu.net
 *  @date 16/1/10  
 */
public class Constant {

    /**
     * 短信发送通道
     */
    public interface SmsSend {

        /**
         * 通知类
         */
        public static final String SEND_NOTIFY = "notify";

        /**
         * 营销类
         */
        public static final String SEND_SALE = "sale";
    }

    /**
     * 短信疲劳标识
     */
    public interface SmsFatigueFlag {

        /**
         * 需要疲劳处理
         */
        public static final String IS_FATIGUE = "1";

        /**
         * 不需要疲劳处理
         */
        public static final String IS_NOT_FATIGUE = "0";
    }

    /**
     * 短信营销类型
     */
    public interface SmsSaleType {

        /**
         * 红包提现提醒
         */
        public static final String HONGBAO_CASH = "hongbao_cash";

        /**
         * 生日提醒
         */
        public static final String SMS_BIRTHDAY = "sms_birthday";

        /**
         * token刷新提醒
         */
        public static final String SMS_TOKEN_REFRESH = "sms_token_refresh";

        /**
         * 短信验证码
         */
        public static final String SMS_VERIFY = "sms_verify";

        /**
         * 营销目的
         */
        public static final String SALE = "sale";

        /**
         * 增粉目的
         */
        public static final String INCREASE_FANS = "increase_fans";

        /**
         * 传播目的
         */
        public static final String SPREAD = "spread";

        /**
         * 其他
         */
        public static final String OTHER = "other";

        /**
         * 测试一下
         */
        public static final String Test = "test";

    }
}
