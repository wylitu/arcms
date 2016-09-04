package com.suniusoft.pay.weixin.menu;

import com.suniusoft.pay.weixin.WeixinConfig;
import com.suniusoft.pay.weixin.WeixinUtil;
import com.suniusoft.pay.weixin.bean.token.WeixinToken;

import java.net.URLEncoder;

public class MenuManager {
	//专家咨询
	private static String btn11_url= WeixinConfig.domain+"/custom/stewardList";
	private static String btn12_url= WeixinConfig.domain+"/custom/chat_member";
	private static String btn13_url= WeixinConfig.domain+"/custom/userInfo";
	private static String btn14_url= WeixinConfig.domain+"/steward/stewardInfo";
	
//	//优惠券活动
//		private static String btn31_url=WeixinConfig.domain2+"couponsInfo_toCouponsInfo.do?oauthWeixinFlag=1";
//	//我的优惠券
//		private static String btn32_url=WeixinConfig.domain2+"couponsInfo_myCouponsInfo.do?oauthWeixinFlag=1";
//	//关注有礼
//		private static String btn33_url=WeixinConfig.domain2+"memberInfo_toweixin.do?oauthWeixinFlag=1";
//	
private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
		.getLogger(MenuManager.class);

	public static void main(String[] args) {
//		String baseUrl=WeixinConfig.oauth2_code_url.replace("APPID", WeixinConfig.appId).replace("SCOPE", "snsapi_userinfo").replace("STATE", "1");
//		
//		String a=baseUrl.replace("REDIRECT_URI", URLEncoder.encode("http://muyixi.oicp.net:8019/huaxiabankSystem/couponsInfo_toCouponsInfo.do"));
//		System.out.println(a);
		// 调用接口获取access_token
		WeixinToken token = WeixinUtil.getWeixinToken();
//		String token = "H7OvT2-RwQyAMdij9HTsBlWyoGk3SoEZ79u75wd7gDJ0urEHQq9vlSPD_dCQ7uICMMUq1tVCp5PfHn-DorYq0ThgZZ9z2NwMc1oHu1j-_90BFPgABAGKU";
		if (null != token) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(),token.getToken());
			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
			//String baseUrl=WeixinConfig.oauth2_code_url.replace("APPID", WeixinConfig.appId).replace("SCOPE", "snsapi_base").replace("STATE", "1");
			String baseUrl=WeixinConfig.oauth2_code_url.replace("APPID", WeixinConfig.appId).replace("SCOPE", "snsapi_userinfo").replace("STATE", "1");
		
			ViewButton btn11 = new ViewButton();  
	        btn11.setName("管家广场");
	        btn11.setType("view"); 
	        btn11.setUrl(baseUrl.replace("REDIRECT_URI", URLEncoder.encode(btn11_url)));
	       
	  
	        ViewButton btn12 = new ViewButton();  
	        btn12.setName("图文咨询");
	        btn12.setType("view");  
	        btn12.setUrl(baseUrl.replace("REDIRECT_URI", URLEncoder.encode(btn12_url)));
		       
	        
	        ViewButton btn13 = new ViewButton();
		btn13.setName("用户中心");
		btn13.setType("view");
		btn13.setUrl(baseUrl.replace("REDIRECT_URI", URLEncoder.encode(btn13_url)));
	
	        ViewButton btn14 = new ViewButton();
	       // btn22.setName("商人圈");  
		btn14.setName("管家中心");
		btn14.setType("view");
		btn14.setUrl(baseUrl.replace("REDIRECT_URI", URLEncoder.encode(btn14_url)));
	
	        ComplexButton mainBtn1 = new ComplexButton();  
	        mainBtn1.setName("车管家");
	        mainBtn1.setSub_button(new Button[] { btn11, btn12,btn13,btn14 });
	        Menu menu = new Menu();
	        menu.setButton(new Button[] { mainBtn1});
	  
	        return menu;  
	}

	/**
	 * 保险菜单
	 *
	 * @return
	 */
	private static Menu getBaoxianMenu() {
		//String baseUrl=WeixinConfig.oauth2_code_url.replace("APPID", WeixinConfig.appId).replace("SCOPE", "snsapi_base").replace("STATE", "1");
		String baseUrl=WeixinConfig.oauth2_code_url.replace("APPID", WeixinConfig.appId).replace("SCOPE", "snsapi_userinfo").replace("STATE", "1");

		ViewButton btn11 = new ViewButton();
		btn11.setName("车险算价");
		btn11.setType("view");
		btn11.setUrl(baseUrl.replace("REDIRECT_URI", URLEncoder.encode("http://wx.chelin8.com/wx/index")));


		ViewButton btn12 = new ViewButton();
		btn12.setName("我的订单");
		btn12.setType("view");
		btn12.setUrl(baseUrl.replace("REDIRECT_URI", URLEncoder.encode("http://wx.chelin8.com/tb/my/order/list")));


		ViewButton btn13 = new ViewButton();
		btn13.setName("0元砍价");
		btn13.setType("view");
		btn13.setUrl(baseUrl.replace("REDIRECT_URI", URLEncoder.encode("http://wx.chelin8.com/ac/cx/activity/index")));


		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("车险团购");
		mainBtn1.setSub_button(new Button[] { btn11,btn12 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("0元砍价");
		mainBtn2.setSub_button(new Button[] { btn13});

//		ComplexButton mainBtn3 = new ComplexButton();
//		mainBtn3.setName("找车管家");
//		mainBtn3.setSub_button(new Button[] { });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1,btn13});

		return menu;
	}


}