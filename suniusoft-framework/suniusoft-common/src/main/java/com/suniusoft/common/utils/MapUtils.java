package com.suniusoft.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;

/**
 *   
 *  @ProjectName: procurement 
 *  @Description:
 *  @author dadi  litu@51xianqu.net
 *  @date 16/1/17  
 */
public class MapUtils {

    public static String toString(Map<String,Object> map){

        if(CollectionUtils.isEmpty(map)){

            return "";

        }

        StringBuffer buf = new StringBuffer();
        buf.append("{");

        Iterator<Map.Entry<String,Object>> i = map.entrySet().iterator();
        boolean hasNext = i.hasNext();
        while (hasNext) {
            Map.Entry<String,Object> e = i.next();
            String key = e.getKey();
            Object value = e.getValue();

            buf.append(key);
            buf.append("=");
            if(value instanceof  String[]){
                buf.append(((String[])value)[0]);
            }else {
                buf.append(value);
            }

            hasNext = i.hasNext();
            if (hasNext)
                buf.append(", ");
        }

        buf.append("}");
        return buf.toString();

    }
}
