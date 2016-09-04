package com.suniusoft.common.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lanpeng on 16/3/14.
 */
public final class BusinessUtil {
    private static final BigDecimal hundred = new BigDecimal(100);
    private BusinessUtil(){}

    public static String goodsCollectProcessCountDesc(Integer totalShare,Integer collectShare){
        if(NumberUtil.isPositiveNumber(totalShare) && NumberUtil.isNumber(collectShare)){
           return new BigDecimal(collectShare).divide(new BigDecimal(totalShare),2,BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(0,BigDecimal.ROUND_HALF_UP).toString()+"%";
        }
        return "--%";
    }

    public static Double goodsCollectProcessCount(Integer totalShare,Integer collectShare){
        if(NumberUtil.isPositiveNumber(totalShare) && NumberUtil.isNumber(collectShare)){
            return new BigDecimal(collectShare).divide(new BigDecimal(totalShare),2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return 0.0;
    }

    public static String generateLuckNum(){
        Random random = new Random();
        Integer num = 10000000+random.nextInt(100000000-10000000);
        return num.toString();
    }

    public static List<String> splitGoodsDetailImg(String path){
        if(StringUtils.isEmpty(path))
            return Collections.emptyList();
        String[] pathArr = path.split(";");
        List<String> pathList = new ArrayList<String>(pathArr.length);
        for(String eachPath : pathArr){
            pathList.add(eachPath);
        }
        return pathList;
    }
    public static void main(String[] args){
        Set<String> nums = new HashSet<String>();
        for(int i = 0;i < 1000;i++){
            String num = generateLuckNum();
            System.out.println(num);
            nums.add(num);
        }

        System.out.println(nums.size());
    }
}
