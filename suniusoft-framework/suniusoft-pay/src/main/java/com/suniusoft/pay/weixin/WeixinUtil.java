package com.suniusoft.pay.weixin;

import com.suniusoft.pay.weixin.bean.token.WeixinToken;
import com.suniusoft.pay.weixin.bean.user.WeixinUserInfo;
import com.suniusoft.pay.weixin.menu.Menu;
import com.suniusoft.pay.weixin.template.TemplateBase;
import com.suniusoft.pay.weixin.template.TemplateSet;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/**
 * 公众平台通用接口工具类
 * 
 * @author wangyong
 */
public class WeixinUtil {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(WeixinUtil.class);


	// 待开发
	private static WeixinToken weixinToken = null;

	/**
	 * 获取access_token
	 * @return
	 */
	public synchronized static WeixinToken getWeixinToken() {
		try {
			JSONObject jsonObject=null;
			if(weixinToken==null||weixinToken.getExpirationTime()<= System.currentTimeMillis()/1000)
			{
				weixinToken=null;
				String requestAccessUrl = WeixinConfig.access_token_url.replace("APPID",WeixinConfig.appId).replace("APPSECRET", WeixinConfig.appSecret);
				jsonObject = httpRequestJSON(requestAccessUrl, "GET", null);
				// 如果请求成功
				if (null != jsonObject) {
					try {
				weixinToken = new WeixinToken();
				weixinToken.setToken(jsonObject.getString("access_token"));
				weixinToken.setExpiresIn(jsonObject.getInt("expires_in"));
				weixinToken.setExpirationTime(System.currentTimeMillis()/1000+jsonObject.getLong("expires_in")-10l);
					} catch (JSONException e) {
				weixinToken = null;
				// 获取token失败
				logger.error(String.format("获取access_token失败 errcode:{%s} errmsg:{%s}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg")));
				return weixinToken;
					}
				}
				if (weixinToken !=null) {
						String requestJsapiUrl = WeixinConfig.jsapi_ticket_token_url.replace("ACCESS_TOKEN", weixinToken.getToken());
						 jsonObject = httpRequestJSON(requestJsapiUrl, "GET", null);
						 if (null != jsonObject) {
								try {
									weixinToken.setTicket(jsonObject.getString("ticket"));
								} catch (JSONException e) {
									weixinToken = null;
									// 获取token失败
									logger.error(String.format("获取jsapi_token失败 errcode:{%s} errmsg:{%s}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg")));
									return weixinToken;
								}
							}
			
				}
	
			}
		} catch (Exception e) {
			logger.error("获取token错误",e);
		}
		return weixinToken;
		}
	
	/**
	 * 用户授权
	 */
	
	public static WeixinUserInfo getUserInfoByOauth2(String code)
	{
		if(null==code||"".equals(code))
		{
			logger.error("getOpenIdByOauth2,code为空");
			return null;
		}
		WeixinUserInfo info =null;
		String url = WeixinConfig.oauth2_openid_url.replace("APPID", WeixinConfig.appId).replace("SECRET", WeixinConfig.appSecret).replace("CODE", code);
		JSONObject jsonObject = httpRequestJSON(url, "GET", null);
		try {
		if (null != jsonObject) {
				String pullUserInfoUrl=WeixinConfig.oauth2_user_info_url.replace("ACCESS_TOKEN", jsonObject.getString("access_token")).replace("OPENID",jsonObject.getString("openid"));
				JSONObject userJsonObject=httpRequestJSON(pullUserInfoUrl,"GET", null);
				if (null != userJsonObject) {
						info= new WeixinUserInfo();
						info.setOpenid(userJsonObject.getString("openid"));
						info.setNickname(userJsonObject.getString("nickname"));
					info.setHeadimgurl(userJsonObject.getString("headimgurl"));
					info.setSex(userJsonObject.getString("sex"));
					info.setProvince(userJsonObject.getString("province"));
					info.setCity(userJsonObject.getString("city"));
				}
				}
			} catch (Exception e) {
			logger.error("getUserInfoByOauth2,code过期");
				return null;
			}
		return info;
	}
	
	/**
	 * 静默授权
	 * @param code
	 * @return
	 */
	public static String getOpenIdByOauth2(String code)
	{
		if(null==code||"".equals(code))
		{
			logger.error("getOpenIdByOauth2,code为空");
			return null;
		}
		String openid = null;
		String url = WeixinConfig.oauth2_openid_url.replace("APPID", WeixinConfig.appId).replace("SECRET", WeixinConfig.appSecret).replace("CODE", code);
		JSONObject jsonObject = httpRequestJSON(url, "GET", null);
		if (null != jsonObject) {
			try {
				openid = jsonObject.getString("openid");
			} catch (Exception e) {
				logger.error("getOpenIdByOauth2,code过期");
				return null;
			}
		
		}
		return openid;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;
		String url = WeixinConfig.menu_create_url.replace("ACCESS_TOKEN", accessToken);
		String jsonMenu = JSONObject.fromObject(menu).toString();
		JSONObject jsonObject = httpRequestJSON(url, "POST", jsonMenu);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error(String.format("创建菜单失败 errcode:{%s} errmsg:{%s}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg")));
			}
		}
		return result;
	}

	/**
	 * 设置行业temp
	 * 
	 * @param templateSet
	 *            实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int templateSet(TemplateSet templateSet, String accessToken) {
		int result = 0;
		String url = WeixinConfig.templat_set_url.replace("ACCESS_TOKEN", accessToken);
		String jsonTemp = JSONObject.fromObject(templateSet).toString();
		System.out.println(jsonTemp);
		JSONObject jsonObject = httpRequestJSON(url, "POST", jsonTemp);
		System.out.println(jsonObject.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error(String.format("设置行业id失败 errcode:{%s} errmsg:{%s}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg")));
			}
		}
		return result;
	}

	/**
	 * 发送模版消息
	 * 
	 * @param templateBase
	 *            实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int sendTemplate(TemplateBase templateBase, String accessToken) {
		int result = 0;
		String url = WeixinConfig.templat_send_url.replace("ACCESS_TOKEN", accessToken);
		String jsonTemp = JSONObject.fromObject(templateBase).toString();
		JSONObject jsonObject = httpRequestJSON(url, "POST", jsonTemp);
		System.out.println(jsonObject.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error(String.format("发送模版消息失败 errcode:{%s} errmsg:{%s}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg")));
			}
		}

		return result;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequestJSON(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		return jsonObject;
	}
	
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return String
	 */
	public static String httpRequestString(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		return null;
	}

	
}