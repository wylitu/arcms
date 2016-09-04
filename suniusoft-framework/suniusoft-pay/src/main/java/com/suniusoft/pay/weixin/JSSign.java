package com.suniusoft.pay.weixin;

import com.suniusoft.pay.weixin.bean.page.JSConfig;
import com.suniusoft.pay.weixin.bean.token.WeixinToken;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Random;

public class JSSign {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(JSSign.class);

	public static JSConfig getJSConfig(String url) {
		WeixinToken token = WeixinUtil.getWeixinToken();
		if (null == token) {
			logger.error("jsconfig 获取token 为空");
			return null;
		}
		JSConfig jsConfig = sign(token.getTicket(), url);
		return jsConfig;
	}

	private static JSConfig sign(String jsapi_ticket, String url) {
		JSConfig jsConfig = null;
		try {
			jsConfig = new JSConfig();
			String nonce_str = createNoncestr();
			String timestamp = create_timestamp();
			String string1;
			String signature = "";

			// 注意这里参数名必须全部小写，且必须有序
			string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
					+ "&timestamp=" + timestamp + "&url=" + url;
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());

			jsConfig.setUrl(url);
			jsConfig.setJsapiTicket(jsapi_ticket);
			jsConfig.setNonceStr(nonce_str);
			jsConfig.setTimestamp(timestamp);
			jsConfig.setSignature(signature);
			jsConfig.setAppId(WeixinConfig.appId);
			jsConfig.setMchId(WeixinConfig.mch_id);

		} catch (Exception e) {
			logger.error("JSConfig sign error", e);
		}
		return jsConfig;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String createNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String createNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
