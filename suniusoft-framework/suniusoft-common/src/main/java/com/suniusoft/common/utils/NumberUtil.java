package com.suniusoft.common.utils;

/**
 * Created by lanpeng on 16/3/7.
 */
public final class NumberUtil {
    private NumberUtil(){}

    public static boolean isPositiveNumber(Integer num){
        return num != null && num > 0;
    }

    public static boolean isPositiveNumber(Long num){
        return num != null && num > 0;
    }

    public static boolean isNumber(Integer num){
        return num != null;
    }

}
