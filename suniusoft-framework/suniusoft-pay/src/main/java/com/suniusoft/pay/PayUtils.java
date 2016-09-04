package com.suniusoft.pay;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wywangyong
 */
public class PayUtils {
    public static DecimalFormat moneyFormat = new DecimalFormat("0.00");

    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    public static SimpleDateFormat formatHMS = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String aliPayEmail="akxxkj@163.com";

    public static String aliPayName="义乌市爱卡信息科技有限公司";

    //流水付款状态
    public static Integer payStatusN = 0;//初始化
    public static Integer payStatusSuccess = 1;
    public static Integer payStatusError = 2;

    //支付类型

    //会员充值消费
    public static Integer memberPayWx = 2;//会员微信充值
    public static Integer memberAiKa = 3;//爱卡币会员消费
    public static Integer memberPayZFB = 6;//会员充值支付宝

    //爱卡币充值
    public static Integer aiKaWx = 1;//爱卡币充值微信
    public static Integer aiKaZFB = 5;//爱卡币充值支付宝

    //订单支付
    public static Integer orderPayOff = 9;//订单支付线下
    public static Integer orderPayAiKa = 8;//订单支付爱卡币
    public static Integer orderPayWx = 4;//订单支付微信
    public static Integer orderPayZFB = 7;//订单支付支付宝


    public static Integer batchPayZFB = 10;//支付宝批量付款


    //支付回调url
    public static String wxNotifyUrl;

    public static String weixinUserInfo="weixinUserInfo";

    public void setWxNotifyUrl(String wxNotifyUrl) {
        PayUtils.wxNotifyUrl = wxNotifyUrl;
    }

    public static String toYuan(Integer money){
        if (money == null){
            return "0";
        }else{
            return moneyFormat.format(new BigDecimal(money).divide(new BigDecimal(100)).doubleValue());
        }

    }

    public static String toYuan(String money){
        return toYuan(Integer.valueOf(money));

    }

    public static Integer toFen(String money){
        if (StringUtils.isEmpty(money)){
            return 0;
        }else{
            return new BigDecimal(money).multiply(new BigDecimal(100)).intValue();
        }

    }

    public static String createOrderNo(Integer orderId){
        StringBuilder sb=new StringBuilder();
        Date nowDate=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String nowDateStr=format.format(nowDate);
        sb.append(nowDateStr+orderId);
        return sb.toString();
    }

}
