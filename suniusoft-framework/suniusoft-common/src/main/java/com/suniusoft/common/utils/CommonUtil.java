package com.suniusoft.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by lin on 2015/6/14.
 */
public class CommonUtil {

    private static Logger log = Logger.getLogger(CommonUtil.class);

    /**
     * 匹配0 或者非0的正整数
     * */
    public static final  String REG_NUMBER = "^(0|[1-9][0-9]*)$";
    /**
     * 匹配小数，小数点后只能有1到4位数字
     * */
    public static final String REG_NUMBERIC = "^[0-9]+(.[0-9]{0,4})?$";

    /**
     *  String to BigDecimal
     * */
    public static BigDecimal getDecimalFromString(String number){
        try{
            if(StringUtils.isBlank(number)){
                throw new IllegalArgumentException(number + " is a not BigDecimal number");
            }
            BigDecimal bd = new BigDecimal(number);
            bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            return bd;
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     *  InputStream to String
     * */
    public static String inputStream2String(InputStream is, String charset) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            return baos.toString(charset);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("CommonUtil.inputStream2String(InputStream is, String charset) occur error:"
                    + e.getMessage());
        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("CommonUtil.inputStream2String(InputStream is, String charset) occur error:"
                            + e.getMessage());
                }
                baos = null;
            }
        }
        return null;
    }

    /**
     *  验证正整数和0
     * */
    public static boolean validateInteger(String source){
        if(StringUtils.isNotBlank(source)){
            return source.matches(REG_NUMBER);
        }
        return false;
    }

    /**
     *  验证小数，小数点后保留1到4位，非负
     * */
    public static boolean validateDouble(String source){

        if(StringUtils.isNotBlank(source)){
            return source.matches(REG_NUMBERIC);
        }
        return false;
    }

    /**
     * 取后几位数
     *
     * @param num 取几位
     * @param str 字符串
     * @return
     */
    public static String lenAfterNum(int num, String str) {
        if (str.length() >= num) {
            return str.substring(str.length() - num, str.length());
        }
        while (str.length() < num) {
            str = "0" + str;
        }
        return str;
    }


    /**
     * 生成指定范围内的随机码
     * @param min 最小值
     * @param max 最大值
     * @return 随机码
     */
    public static int getRandomCode(final int min, final int max){
        Random rand= new Random();

        int tmp = Math.abs(rand.nextInt());

        return tmp % (max - min + 1) + min;

    }


    /**
     * 对应javascript的unescape()函数, 可对javascript的escape()进行解码
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     *  给定一个路径(window下或者linux下生成的), 按照路径分隔符split,
     *  window下的路径可能在linux系统下做split, 反之也亦然
     *  window下路径分隔符为 \, linux下路径分隔符为 /
     *  @param path
     * */
    public static String[] splitPath(String path){
        if(StringUtils.isNotBlank(path)){
            String currentPathChar = File.separator;
            String replaceChar = "";
            if("/".equals(currentPathChar)){
                replaceChar = "\\";
            }
            if("\\".equals(currentPathChar)){
                replaceChar = "/";
                currentPathChar = "\\";
            }
            path = replaceAll(path, replaceChar,currentPathChar);
            if("\\".equals(currentPathChar)){
                currentPathChar += "\\";
            }
            return path.split(currentPathChar);
        }
        return null;
    }

    /**
     * 字符串替换，从头到尾查询一次，替换后的字符串不检查
     * @param str     源字符串
     * @param oldStr  目标字符串
     * @param newStr  替换字符串
     * @return        替换后的字符串
     */
    public static String replaceAll(String str, String oldStr, String newStr){
        int i = str.indexOf(oldStr);
        int n = 0;
        while(i != -1)
        {
            str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
            i = str.indexOf(oldStr, i + newStr.length());
            n++;
        }
        return str;
    }

}
