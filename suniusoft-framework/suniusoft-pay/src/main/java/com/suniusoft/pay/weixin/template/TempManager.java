package com.suniusoft.pay.weixin.template;

import com.suniusoft.pay.weixin.WeixinConfig;
import com.suniusoft.pay.weixin.WeixinUtil;
import com.suniusoft.pay.weixin.bean.token.WeixinToken;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class TempManager {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(TempManager.class);
	private static String DefalutColor="#173177";
	
	

	public static void creatIndustryId()
	{
		
		WeixinToken token = WeixinUtil.getWeixinToken();
		if (null != token) {
			TemplateSet templateSet= new TemplateSet();
			templateSet.setIndustry_id1("32");
			templateSet.setIndustry_id2("41");
			int result = WeixinUtil.templateSet(templateSet, token.getToken());
			if (0 == result)
				log.info("设置行业模版id成功！");
			else
				log.info("设置行业模版id失败，错误码：" + result);
		}
		
	}
	//当后台审核通过的时候，发送给车管家，有新任务
	public static void sendStewardToWork(String openId,String work){
		String tempId="3Q6JQ2G71hRYLdrEtxk7CT4TWx0P6IyARrPbmo7oZBA";
		Map map= new HashMap();
		TemplateElement tempFirst = new TemplateElement();
		tempFirst.setValue("新订单提醒");
		tempFirst.setColor(DefalutColor);
		map.put("first", tempFirst);

		TemplateElement tempKeyword1 = new TemplateElement();
		tempKeyword1.setValue(work);
		tempKeyword1.setColor(DefalutColor);
		map.put("work", tempKeyword1);

		TemplateElement tempKeyword2 = new TemplateElement();
		tempKeyword2.setValue("请快点接单吧");
		tempKeyword2.setColor(DefalutColor);
		map.put("remark", tempKeyword2);

		TemplateBase tb=new TemplateBase();
		tb.setTouser(openId);
		tb.setTemplate_id(tempId);
		tb.setTopcolor(DefalutColor);
		tb.setUrl(WeixinConfig.domain+"/steward/orderList");
		tb.setData(map);
		String jsonTemp = JSONObject.fromObject(tb).toString();
		WeixinToken token = WeixinUtil.getWeixinToken();
		if (null != token) {
			int result = WeixinUtil.sendTemplate(tb, token.getToken());
			if (0 == result)
				log.info("发送行业模版信息成功！");
			else
				log.info("发送行业模版信息失败，错误码：" + result);
		}
	}

	//订单受理，发给用户
	public static void sendUser(String openId,String work){
		String tempId="iIpLKmRrPUwDRvY4HWsVAtced2GXCT8R43VmMFxp0uM";
		Map map= new HashMap();
		TemplateElement tempFirst = new TemplateElement();
		tempFirst.setValue("订单受理提醒");
		tempFirst.setColor(DefalutColor);
		map.put("first", tempFirst);

		TemplateElement tempKeyword1 = new TemplateElement();
		tempKeyword1.setValue(work);
		tempKeyword1.setColor(DefalutColor);
		map.put("keyword1", tempKeyword1);

		TemplateElement tempKeyword3 = new TemplateElement();
		tempKeyword3.setValue("后台审核通过，车管家开始介入");
		tempKeyword3.setColor(DefalutColor);
		map.put("keyword2", tempKeyword1);

		TemplateElement tempKeyword2 = new TemplateElement();
		tempKeyword2.setValue("感谢您的使用");
		tempKeyword2.setColor(DefalutColor);
		map.put("remark", tempKeyword2);

		TemplateBase tb=new TemplateBase();
		tb.setTouser(openId);
		tb.setTemplate_id(tempId);
		tb.setTopcolor(DefalutColor);
		tb.setUrl(WeixinConfig.domain+"/steward/orderList");
		tb.setData(map);
		String jsonTemp = JSONObject.fromObject(tb).toString();
		WeixinToken token = WeixinUtil.getWeixinToken();
		if (null != token) {
			int result = WeixinUtil.sendTemplate(tb, token.getToken());
			if (0 == result)
				log.info("发送行业模版信息成功！");
			else
				log.info("发送行业模版信息失败，错误码：" + result);
		}
	}

	//订单受理，发给用户
	public static void sendMessage(String openId,String work){
		String tempId="mYY8hdafLKytCm-Dr1LTs9P7rmj4ijeV1E4hrWB5NF0";
		Map map= new HashMap();
		TemplateElement tempFirst = new TemplateElement();
		tempFirst.setValue("消息提醒");
		tempFirst.setColor(DefalutColor);
		map.put("first", tempFirst);

		TemplateElement tempKeyword1 = new TemplateElement();
		tempKeyword1.setValue(work);
		tempKeyword1.setColor(DefalutColor);
		map.put("keyword1", tempKeyword1);

		TemplateElement tempKeyword3 = new TemplateElement();
		tempKeyword3.setValue("后台审核通过，车管家开始介入");
		tempKeyword3.setColor(DefalutColor);
		map.put("keyword2", tempKeyword1);

		TemplateElement tempKeyword2 = new TemplateElement();
		tempKeyword2.setValue("感谢您的使用");
		tempKeyword2.setColor(DefalutColor);
		map.put("remark", tempKeyword2);

		TemplateBase tb=new TemplateBase();
		tb.setTouser(openId);
		tb.setTemplate_id(tempId);
		tb.setTopcolor(DefalutColor);
		tb.setUrl(WeixinConfig.domain+"/steward/orderList");
		tb.setData(map);
		String jsonTemp = JSONObject.fromObject(tb).toString();
		WeixinToken token = WeixinUtil.getWeixinToken();
		if (null != token) {
			int result = WeixinUtil.sendTemplate(tb, token.getToken());
			if (0 == result)
				log.info("发送行业模版信息成功！");
			else
				log.info("发送行业模版信息失败，错误码：" + result);
		}
	}
	

	public static void main(String[] args) throws Exception {
//String first,String keyword1,String keyword2,String keyword3,String remark,String openId,String url
//TempManager.sendStewardToWork("o9td8s6UvMP7YFvF8WseLcm9CMnE","teest");
		URL url = new URL("http://115.29.171.5:9001/smsgbk.aspx?action=send&userid=2048&account=hzlu&password=123456&mobile=15968192955&content=【八公车管家】您的短信验证码为：213212，请及时输入&sendTime=&taskName=短信验证码&checkcontent=1&mobilenumber=1&countnumber=1&telephonenumber=1");
		HttpURLConnection httpUrlConn = (HttpURLConnection) url
				.openConnection();

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
			httpUrlConn.connect();



		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);
StringBuilder buffer = new StringBuilder();
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
	}


}