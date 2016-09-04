package com.arcms.listener;

import com.suniusoft.common.utils.SMSUtils;

import javax.servlet.ServletContextEvent;
import java.io.InputStream;
import java.util.Properties;

/**
 * @desc 扩展contextLoad功能,能够对log4j
 *       的配置文件进行替换,实现日志的配置功能
 * @company shufensoft
 * @author litu
 * @date 2015/4/8.
 * @version 1.0
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {

    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
    }

    public void contextInitialized(ServletContextEvent sce) {

        Properties props = new Properties();
        try {
            InputStream in = ContextLoaderListener.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(in);
            String loggingRoot = props.getProperty("loggingRoot");
            String loggingLevel = props.getProperty("loggingLevel");
            System.setProperty("loggingRoot", loggingRoot);
            System.setProperty("loggingLevel", loggingLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.contextInitialized(sce);

    }

    public static void main(String[] args) {

        System.out.print(SMSUtils.sendSmsCode("18057138887", "1234"));

    }

}
