package com.suniusoft.common.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lin on 2015/6/15.
 */
public class CsvUtils {

    /**
     * 此方法适用一张表对应一个实体类
     * @param columnPropertyMapping 实体类属性名与CSV文件中列名的对应关系
     * @param inputStream 上传的CSV文件流
     * @param clazz 处理的实体类class
     * */
    public static <T> List<T> parseCSVToBeanList(Map<String,String> columnPropertyMapping,
                                                  InputStream inputStream, Class<T> clazz) throws Exception {
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
        Reader reader = new InputStreamReader(inputStream);
        CSVParser parser = new CSVParser(reader, format);
        List<T> list = new ArrayList<T>();
        for(CSVRecord record : parser){
            T obj = fillObjectValue(clazz,record,columnPropertyMapping);
            list.add(obj);
        }
        return list;
    }

    /**
     *
     * 给实体类填充属性值
     *
     * */
    private static <T> T fillObjectValue(Class<T> c, CSVRecord record, Map<String,String> columnPropertyMapping) throws  Exception{
        T obj = c.newInstance();
        for(Map.Entry<String,String> entry : columnPropertyMapping.entrySet()){
            String proName = entry.getKey();
            String columnName = entry.getValue();
            Field f = c.getDeclaredField(proName);
            f.setAccessible(true);
            f.set(obj,record.get(columnName));
        }
        return obj;
    }

    /**
     *
     * */
    public static CSVParser getCSVParser(InputStream inputStream) throws Exception{
        CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(',');
        Reader reader = new InputStreamReader(inputStream,"gbk");
        CSVParser parser = new CSVParser(reader, format);
        return parser;
    }

}