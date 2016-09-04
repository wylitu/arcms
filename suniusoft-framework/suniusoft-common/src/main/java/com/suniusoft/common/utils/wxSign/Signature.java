package com.suniusoft.common.utils.wxSign;

import com.suniusoft.common.utils.Md5Encrypt;
import com.suniusoft.common.utils.StringUtils;

import java.lang.reflect.Field;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: <p>
 *                  微信端登陆签名
 *                 </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/19  
 */
public class Signature {

    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o) throws IllegalAccessException {

        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + Configure.getKey();
        System.out.println("Sign Before MD5:" + result);
        result = Md5Encrypt.md5(result).toUpperCase();
        System.out.println("Sign Result:" + result);
        return result;
    }

    public static String getSign(Map<String,Object> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=null && entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + Configure.getKey();
        System.out.println("Sign Before MD5:" + result);
        result = Md5Encrypt.md5(result).toUpperCase();
        System.out.println("Sign Result:" + result);
        return result;
    }

    public static void main(String[] args) {

        Map<String,Object> map = new HashMap<String, Object>();

        map.put("mobile", "dd");
        map.put("validateRepeat", "dd");
        String sign = getSign( map);
        String sig="C821F5E2908299CE78D0D2484E67A0F9";

        if (StringUtils.isBlank(sig) || !sig.equals(sign)) {


            System.out.println("dffd");

        }
        System.out.println("test");


    }



}
