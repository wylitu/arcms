package com.suniusoft.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @desc json帮助类
 * @company shufensoft
 * @author litu
 * @date 2015/4/8.
 * @version 1.0
 */
public class JSONUtils {

    /**
     * 将一个 Object 或者List对象转化为JSONObject或者JSONArray
     * @param ObjOrList  Object 或者List对象
     * @return
     */
    public static Object toJSON(Object ObjOrList)
    {
        Object obj=null;

        try {
            obj= JSON.toJSON(ObjOrList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }


    /**
     * 将一个 Object 或者List对象转化为JSOObject或者JSONArray
     * @param ObjOrList  Object 或者List对象 或者hashmap 但是如果是set  就会有问题
     * @return
     */
    public static String toJSONStr(Object ObjOrList)
    {
        String  jsonstr="";

        try {
            jsonstr=JSON.toJSONString(ObjOrList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonstr;
    }


    //第二部分字符串转  obj list


    /**
     * 字符串转obj
     * @param jsonstr
     * @param clazz
     * @return
     */
    public static Object parseToObject(String jsonstr,Class<?> clazz)
    {
        Object parseObj=null;
        try {
            parseObj=JSON.parseObject(jsonstr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseObj;
    }

    /**
     * 字符串转list
     * @param jsonstr
     * @param clazz
     * @return
     */
    public static List<?> parseToList(String jsonstr,Class<?> clazz)
    {
        List<?> parseObj=null;
        try {
            parseObj=JSON.parseArray(jsonstr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseObj;
    }


    //第三部分  字符串转JSONObj  或者JSONArray

    /**
     * 字符串转jsonobj
     * @param jsonstr
     * @param jsonObjectClass
     * @return
     */
    public static JSONObject parseToJSONObejct(String jsonstr, Class<JSONObject> jsonObjectClass)
    {
        JSONObject parseObj=null;
        try {
            parseObj=JSON.parseObject(jsonstr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseObj;
    }

    /**
     * 字符串转list
     * @param jsonstr
     * @return
     */
    public static JSONArray parseToJSONArray(String jsonstr)
    {
        JSONArray parseObj=null;
        try {
            parseObj=JSON.parseArray(jsonstr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseObj;
    }



    //第四部分 com.alibaba包下 JSONObj 或者JSONArr 转 javabean或者 java array
    /**
     *
     * @param jsonObj
     * @param obj
     * @return
     */
    public static Object parseToObject(JSONObject jsonObj,Class<?> obj)
    {
        Object parseObj=null;
        try {
            parseObj=JSON.parseObject(jsonObj + "", obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseObj;
    }

    /**
     *
     * @param jsonArr
     * @param obj
     * @return
     */
    public static List<?> parseToList(JSONObject jsonArr,Class<?> obj)
    {
        List list=null;

        try {
            list=JSON.parseArray(jsonArr + "", obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /***
     *   针对ueditor上传图片返回的字符串做修改，把url前面加上项目路径前缀
     * */
    public static JSONObject modify(String jsonStr, String baseUrl){
        JSONObject json = JSON.parseObject(jsonStr);
        if(StringUtils.isNotBlank(baseUrl) && baseUrl.length() > 1){        //排除baseUrl为 / 的情况，如果是/icard就要加上
            String state = json.getString("state");
            if("SUCCESS".equals(state)){
                String url = json.getString("url");
                json.remove("url");
                json.put("url", baseUrl + url);
            }
        }
        return json;
    }

}
