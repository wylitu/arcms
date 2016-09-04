package com.suniusoft.pay.weixin;

public class WeixinConfig {
	
	//test
//	public final static String appId = "wxda383e3a1d1439ce";
//	public final static String appSecret = "268f875686e63d019947686d7ee038ef";
//	public final static String token = "foxfoxfox";
//	public final static String EncodingAESKey="Iv9yIivQjaXpEnz3HMeRcstEdE1HbEo17alY4D8PCsW";
//	public static String domain="http://lawyer.tunnel.mobi/Lawyer/";
	
	public static String domain="http://muyixi.oicp.net";
	public static String domain2="http://muyixi.oicp.net:8019/huaxiabankSystem/";
	public final static String appId = "wx93fa7f7554f27eec";//"wx49181b7ad3126302";//"wx93fa7f7554f27eec";
	public final static String appSecret = "993607091f4bd8036386bf06ab00d945";
	public final static String token = "changrongchangrong";
	public final static String EncodingAESKey="4h19Un4cuGzEoU6udQi2TnTUzoAoCjFm41OkM9nNbLL";
	
	public final static String session_weixin_open_id="weixin_open_id";
	public final static String session_weixin_nickname="weixin_nickname";
	public final static String oauth2_code_url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public final static String oauth2_openid_url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//拉取用户信息
	public final static String oauth2_user_info_url="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String jsapi_ticket_token_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 菜单创建（POST） 限100（次/天）
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 设置行业模版id（32）
	public final static String templat_set_url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	// 获取模版
	public final static String templat_get_url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	// 发送模版
	public final static String templat_send_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	//支付统一接口
	public final static String unified_order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	
	//签名加密方式
	public final static String sign_type = "MD5";
	public final static String trade_type_jsapi = "JSAPI";
	public final static String mch_id="1266146001";//"1266146001" 1254805401;
	public final static String mch_Key="zhjiangak6416846846sdafadc225574";//"zhjiangak6416846846sdafadc225574";//"357tyugu7858s4u47po45f670ccvae45";

	public final static String pay_notify_url="/weixinPay/payNodify.do";

}
