package com.suniusoft.common.api.helper;


import com.suniusoft.common.utils.CommonUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/12 22:51  
 */
public class WXHelper {
    /**
     * 功能：将安全校验码和参数排序
     * @param params 参数集合
     * @param privateKey 安全校验码
     * */
    public static String getContent(Map params, String privateKey) {

        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);

            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr + "&key="+privateKey;

    }

    public static String createNoncestr() {

        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 根据长度构建Noncestr
     * @param length
     * @return
     */
    public static String createNoncestrByLen(int  length) {

        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }


    /**
     * 随机生成商户订单号,
     */
    public  static String  createMchId(String  userId){
        String orderId = "10000" ;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String  orderNum=df.format(date);
        Random rad = new Random();
        String   threeNum= rad.nextInt(1000) + "";
        String   lenAfterNum= CommonUtil.lenAfterNum(4,userId);
        return   orderId+orderNum+threeNum+lenAfterNum;
    }

}
