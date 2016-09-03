package com.suniusoft.common.utils;

import com.suniusoft.common.utils.exception.HttpClientException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: 基于 httpclient 4.3.1版本的 http工具类
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/12 22:29  
 *
 * @modify by litu  2015/4/13 15:29  
 */
public class HttpUtils {

    private static CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, CHARSET);
    }

    public static String doGet(HttpGet request) {
        return doGet(CHARSET, request);
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, CHARSET);
    }

    public static String doPost(HttpPost httpPost) {
        return doPost(httpPost, CHARSET);
    }


    /**
     * HTTP Get 获取内容
     *
     * @param charset 编码格式
     * @param request get请求
     * @return 页面内容
     */
    public static String doGet(String charset, HttpGet request) {

        if (request == null) {
            throw new HttpClientException("request is null");
        }
        try {

            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                request.abort();
                throw new HttpClientException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;

        } catch (Exception e) {
            throw new HttpClientException(e.getMessage(), e);
        }

    }


    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) {

        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);

            return doGet(charset, httpGet);

        } catch (Exception e) {
            throw new HttpClientException(e.getMessage(), e);
        }

    }

    public static final String CERT_PATH = "D:\\baidu cloud\\work\\crm\\apiclient_cert.p12";


    /**
     * @param url      地址
     * @param params   参数
     * @param charset  字符集
     * @param certPath SSL证书路径
     * @param mchId    商户号
     * @return
     */
    public static String doPost(String url, String params, String charset, String certPath, String mchId) {
        try {
            HttpPost httpPost = new HttpPost(url);


            httpPost.setEntity(new StringEntity(params));
            SSLConnectionSocketFactory sslsf = null;
            if (!StringUtils.isBlank(certPath)) {
                try {
                    sslsf = getSSL(certPath, mchId);

                } catch (Exception e) {

                }
                httpClient = HttpClients.custom()
                        .setSSLSocketFactory(sslsf)
                        .build();
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new HttpClientException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            throw new HttpClientException(e.getMessage(), e);
        }

    }


    private static SSLConnectionSocketFactory getSSL(String certPath, String mchId) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File(certPath));
        try {
            //指定PKCS12的密码(商户ID)
            keyStore.load(instream, mchId.toCharArray());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray()).build();
        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//        //设置httpclient的SSLSocketFactory
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .build();
        return sslsf;
    }

    /**
     * HTTP Post 获取内容
     *
     * @param httpPost post请求
     * @param charset  编码格式
     * @return 页面内容
     */
    public static String doPost(HttpPost httpPost, String charset) {

        if (httpPost == null) {
            throw new HttpClientException("request is null");
        }

        try {
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setBufferSize(8128)
                    .build();

            httpClient = HttpClients.custom()
                    .setDefaultConnectionConfig(connectionConfig)
                    .build();
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new HttpClientException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;

        } catch (Exception e) {
            throw new HttpClientException(e.getMessage(), e);
        }
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }

            return doPost(httpPost, charset);
        } catch (Exception e) {
            throw new HttpClientException(e.getMessage(), e);
        }
    }

    /**
     * @author litu
     * @param url
     * @param jsonStr
     * @return
     */
    public static  String doJsonPost(String url, String jsonStr) {

        return doJsonPost(url, jsonStr, CHARSET);

    }

    /**
     * HTTP json Post 获取内容
     * @author litu
     * @param url     请求的url地址 ?之前的地址
     * @param jsonStr 请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doJsonPost(String url, String jsonStr, String charset) {

        if (StringUtils.isBlank(url)) {
            return null;
        }

        try {

            HttpPost httpPost = new HttpPost(url);

            StringEntity entity = new StringEntity(jsonStr, "utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            return doPost(httpPost, charset);
        } catch (Exception e) {
            throw new HttpClientException(e.getMessage(), e);
        }

    }

}
