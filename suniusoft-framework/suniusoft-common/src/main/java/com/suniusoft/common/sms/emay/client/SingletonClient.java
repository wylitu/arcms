package com.suniusoft.common.sms.emay.client;


import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 亿美短信单例客户端
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/10 0:37  
 */
public class SingletonClient {
    private static Client client = null;

    private SingletonClient() {
    }

    public synchronized static Client getClient(String softwareSerialNo, String key) {
        if (client == null) {
            try {
                client = new Client(softwareSerialNo, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public synchronized static Client getClient() {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("config");
        if (client == null) {
            try {
                client = new Client(bundle.getString("softwareSerialNo_notify"), bundle.getString("key_notify"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public synchronized static Client getClient(String type) {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("config");

        String softwareSeriaNo = null;
        String key = null;

        //if (client == null) {
            try {
                if ("sale".equals(type)) {
                    softwareSeriaNo = bundle.getString("softwareSerialNo_sale");
                    key = bundle.getString("key_sale");
                } else if ("notify".equals(type)) {
                    softwareSeriaNo = bundle.getString("softwareSerialNo_notify");
                    key = bundle.getString("key_notify");
                } else {
                    throw new IllegalArgumentException("type 参数不正确。");
                }
                client = new Client(softwareSeriaNo, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}
        return client;
    }


}
