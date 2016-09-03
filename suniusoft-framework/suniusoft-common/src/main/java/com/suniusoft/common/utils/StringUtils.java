package com.suniusoft.common.utils;

import java.util.*;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: <p>
 *                 字符串工具类
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/19  
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{

    /**
     * 判断不能不包含blank
     * @param str
     */
    public static boolean isNotContainsBlank(String str) {

        if(isBlank(str)){
            return false;
        }

        if(!isNotBothSidesBlank(str)){
            return false;
        }

        String str1 = str.replaceAll(" ", "") ;

        if (!str1.equals(str)) {
            return false;
        }

        return true;
    }

    /**
     * 判断两边不能为blank
     * @param str
     */
    public static boolean isNotBothSidesBlank(String str) {

        if(isBlank(str)){
            return false;
        }

        String str1 = str.trim();

        if (!str1.equals(str)) {
           return false;
        }

        return true;
    }

    private static final String RE_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean isEmail(String str) {
        if(str != null && str.matches(RE_EMAIL)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMobile(String str) {
        if(str == null || str.length() != 11) {
            return false;
        }

        for(int i=0; i< str.length(); i++) {
            char c = str.charAt(i);
            if(c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        boolean result = isMobile("15867165117");
        System.out.println(result);
    }

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 解析前台传入的字符串，如“id1:mobile1,id2:mobile2,....”,得到手机号码或邮箱的列表
     */
    public static Map<String,Object> getInfoMap(String str){
        Map<String,Object> map = new HashMap<String, Object>();

        if (str == null||str.equals("")){
            return null;
        }
        List<String> infoList = new ArrayList<String>();
        List<String> idList = new ArrayList<String>();
        String[] temp = str.split(",");
        for (int i=0;i<temp.length;i++){
            String subStr = temp[i];
            String[] subTemp = subStr.split(":");
            idList.add(subTemp[0]);
            infoList.add(subTemp[1]);
        }
        map.put("idList",idList);
        map.put("infoList",infoList);
        return map;
    }

}
