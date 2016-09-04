package com.suniusoft.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *   
 *  @ProjectName: sf_crm  
 *  @Description: 日期转换类 
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/5/23 20:59  
 */
public class DateConvertUtils {


        /**
         * 日期转换成字符串
         * @param date
         * @return str
         */
        public static String dateToStr(Date date) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = format.format(date);
            return str;
        }


        public static String dateToStrSerial(Date date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String str = format.format(date);
            return str;
        }


        public static String dateToStr(Date date, String formatStr) {

            SimpleDateFormat format = new SimpleDateFormat(formatStr);

            String str = format.format(date);

            return str;
        }

        /**
         * 字符串转换成日期
         * @param str
         * @return date
         */
        public static Date strToDate(String str) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }



    }
