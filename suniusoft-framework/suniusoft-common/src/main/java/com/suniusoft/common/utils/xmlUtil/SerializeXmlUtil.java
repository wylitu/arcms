package com.suniusoft.common.utils.xmlUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;

/**
 *   
 *  @ProjectName: icard  
 *  @Description: <p>
 *               </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/7/6  
 */
public class SerializeXmlUtil {

    public static XStream createXstream() {
        return new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    boolean cdata = false;
                    Class<?> targetClass = null;

                    @Override
                    public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
                        super.startNode(name, clazz);
                        // 业务处理，对于用XStreamCDATA标记的Field，需要加上CDATA标签
                        if (!name.equals("xml")) {
                            cdata = needCDATA(targetClass, name);
                        } else {
                            targetClass = clazz;
                        }
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
    }

    private static boolean needCDATA(Class<?> targetClass, String fieldAlias) {
        boolean cdata = false;
        // first, scan self
        cdata = existsCDATA(targetClass, fieldAlias);
        if (cdata)
            return cdata;
        // if cdata is false, scan supperClass until java.lang.Object
        Class<?> superClass = targetClass.getSuperclass();
        while (!superClass.equals(Object.class)) {
            cdata = existsCDATA(superClass, fieldAlias);
            if (cdata)
                return cdata;
            superClass = superClass.getClass().getSuperclass();
        }
        return false;
    }

    private static boolean existsCDATA(Class<?> clazz, String fieldAlias) {
        if ("MediaId".equals(fieldAlias)) {
            return true; // 特例添加 morning99
        }
        // scan fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 1. exists XStreamCDATA
            if (field.getAnnotation(XStreamCDATA.class) != null) {
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                // 2. exists XStreamAlias
                if (null != xStreamAlias) {
                    if (fieldAlias.equals(xStreamAlias.value()))// matched
                        return true;
                } else {// not exists XStreamAlias
                    if (fieldAlias.equals(field.getName()))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     *  转化为xml
     * @param obj
     * @param cls
     * @return
     */
    public static  String toXml(Object obj, Class<?> cls){

        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(cls);
        xs.alias("xml", cls);

        return xs.toXML(obj);

    }

    /**
     * 解析xml
     * @param in
     * @param cls
     * @return
     * @throws IOException
     */
    public static  Object parseXml(InputStream in, Class<?> cls) throws IOException{

        /**
         * 读取接收到的xml消息
         */
        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(cls);
        xs.alias("xml", cls);
        StringBuilder xmlMsg = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            xmlMsg.append(new String(b, 0, n, "UTF-8"));
        }
        return xs.fromXML(xmlMsg.toString());

    }


}
