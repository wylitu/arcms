package com.suniusoft.common.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *   
 *  @ProjectName: sf-parent  
 *  @Description: 
 *  @author chenqiang  chenqiang@shufensoft.com
 *  @date 2015-05-26 16:33  
 */
public class DateUtil {

    private static Logger log = Logger.getLogger(DateUtil.class);
    /**
     * yyyy-MM-dd HH:mm:ss 格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm 格式
     */
    public static final String DEFAULT_DATE_TIME_HHmm_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH 格式
     */
    public static final String DEFAULT_DATE_TIME_HH_FORMAT_PATTERN = "yyyy-MM-dd HH";
    /**
     * yyyy-MM-dd 格式
     */
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    /**
     * HH:mm:ss 格式
     */
    public static final String DEFAULT_TIME_FORMAT_PATTERN = "HH:mm:ss";
    /**
     * HH:mm 格式
     */
    public static final String DEFAULT_TIME_HHmm_FORMAT_PATTERN = "HH:mm";
    /**
     * yyyy/MM/dd HH:mm 格式
     */
    public static final String DEFAULT_DATE_TIME_HHmm_FORMAT_PATTERN_1 = "yyyy/MM/dd HH:mm";

    /**
     * 年
     * <p>可以通过DateTime.now().get(DateTime.YEAR_FIELD)来获取当前时间的年</p>
     */
    public static final int YEAR_FIELD = Calendar.YEAR;
    /**
     * 月
     * <p>可以通过DateTime.now().get(DateTime.MONTH_FIELD)来获取当前时间的月</p>
     */
    public static final int MONTH_FIELD = Calendar.MONTH;
    /**
     * 日
     * <p>可以通过DateTime.now().get(DateTime.DAY_FIELD)来获取当前时间的日</p>
     */
    public static final int DAY_FIELD = Calendar.DATE;
    /**
     * 小时 <p>可以通过DateTime.now().get(DateTime.HOUR_FIELD)来获取当前时间的小时</p>
     */
    public static final int HOUR_FIELD = Calendar.HOUR_OF_DAY;
    /**
     * 分钟 <p>可以通过DateTime.now().get(DateTime.MINUTE_FIELD)来获取当前时间的分钟</p>
     */
    public static final int MINUTE_FIELD = Calendar.MINUTE;
    /**
     * 秒
     * <p>可以通过DateTime.now().get(DateTime.SECOND_FIELD)来获取当前时间的秒</p>
     */
    public static final int SECOND_FIELD = Calendar.SECOND;
    /**
     * 毫秒 <p>可以通过DateTime.now().get(DateTime.MILLISECOND_FIELD)来获取当前时间的毫秒</p>
     */
    public static final int MILLISECOND_FIELD = Calendar.MILLISECOND;
    private Calendar c;   //日历类
    /**
     * yyyy-MM-dd  格式
     */
    private static final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * yyyy-MM-dd HH 格式
     */
    private static final SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    /**
     * yyyy-MM-dd HH:mm:ss 格式
     */
    private static final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 将字符串日期转换为日期类型，yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr 字符串日期
     * @return Date:yyyy-MM-dd HH:mm:ss
     */
    public static Date strToDate(String dateStr) {
        Date date = null;
        try {
            date = longSdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 取指定天数后的日期，时分秒置为0,一般用来计算若干天后的过期日期
     *
     * @param days
     * @return
     */
    public static final Date getExpiredDay(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DATE, days);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 指定日期与当前日期相差的天数
     *
     * @param date
     * @return
     */
    public static final int getBetweenDays(Date date) {
        long milliSeconds = System.currentTimeMillis() - date.getTime();

        return (int) (milliSeconds / 1000 / 24 / 3600);
    }

    /**
     * 获得本月的开始时间，如2015-06-01 00:00:00
     *
     * @return
     */
    public static final Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取昨天结束时间
     *
     * @return
     */
    public static final Date getYearterDayEndTime() {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            //c.set(Calendar.DATE,1);
            c.add(Calendar.DATE, -1);
            date = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取N天前的结束时间 23:59:59
     *
     * @param n
     * @return
     */
    public static final Date getNDayEndTime(Integer n) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            c.add(Calendar.DATE, -n);
            date = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取N天前的开始时间 00：00：00
     *
     * @param n
     * @return
     */
    public static final Date getNDayStartTime(Integer n) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            c.add(Calendar.DATE, -n);
            date = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 当前月的结束时间，如2015-06-31 23:59:59
     *
     * @return
     */
    public static final Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * data 转换成 字符串
     * 格式为： yyyy-MM-dd HH:mm:ss
     */
    public static String getDateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_PATTERN);
        return format.format(date);
    }

    /**
     * data 转换成 字符串
     * 格式为： yyyy-MM-dd HH:mm:ss
     */
    public static String getDateToString2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_HHmm_FORMAT_PATTERN);
        return format.format(date);
    }

    /**
     * 格式为yyyy-MM-dd HH:mm:ss字符串转换成date
     */
    public static Date getDateFromString(String datesStr) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_PATTERN);
        try {
            if (StringUtils.isBlank(datesStr)) {
                throw new IllegalArgumentException("datesStr is null");
            }
            return format.parse(datesStr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * yyyy/MM/dd HH:mm格式的字符串转换成yyyy-MM-dd HH:mm格式的date
     */
    public static Date getDateFromStr(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_HHmm_FORMAT_PATTERN_1);
        try {
            Date d = format.parse(str);
            String ss = getDateToString(d);
            return getDateFromString(ss);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static Date addYear(Date date, int num) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(1, num);
        return gc.getTime();
    }

    public static void main(String[] agrs) {

        Date date = addYear(new Date(), 2);

        System.out.print(date);
    }


}
