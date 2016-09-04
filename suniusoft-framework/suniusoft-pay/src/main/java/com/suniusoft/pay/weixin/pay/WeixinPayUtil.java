package com.suniusoft.pay.weixin.pay;

import com.suniusoft.pay.weixin.JSSign;
import com.suniusoft.pay.weixin.SignUtil;
import com.suniusoft.pay.weixin.WeixinConfig;
import com.suniusoft.pay.weixin.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeixinPayUtil {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(WeixinPayUtil.class);


	/**
	 * 获取微信jsapi页面所需参数
	 * 
	 * @param body
	 *            （商品描述）
	 * @param out_trade_num
	 *            （订单号，不可重复）
	 * @param total_fee
	 *            （金额（分为单位））
	 * @param notify_url
	 *            （通知地址）
	 * @param request
	 * 			
	 * @return
	 */
	public static String getWeixinJSAPIParams(String body,String out_trade_num,String total_fee,String notify_url,String sendSucUrl,String sendFailUrl,HttpServletRequest request,String openId)
    {
        SortedMap<Object,Object> pageParamsMap = new TreeMap<Object,Object>();
        pageParamsMap.put("self_result", "fail");
        pageParamsMap.put("self_msg", "请求支付失败，请稍候重试");
        //判断版本
        if(!isWeiXinPay(request))
        {
            pageParamsMap.put("self_msg", "微信版本不支持支付，请升级微信版本");	
            return JSONObject.fromObject(pageParamsMap).toString();
        }
        try {
        //统一下单
        SortedMap<Object, Object> contentMap = new TreeMap<Object, Object>();
        contentMap.put("appid", WeixinConfig.appId); // 公众账号 ID
        contentMap.put("mch_id", WeixinConfig.mch_id); // 商户号
        contentMap.put("nonce_str", JSSign.createNoncestr()); // 随机字符串
        contentMap.put("body", body); // 商品描述
        contentMap.put("out_trade_no",out_trade_num); // 商户订单号
        contentMap.put("total_fee",total_fee); // 订单总金额
        contentMap.put("spbill_create_ip",request.getRemoteAddr()); // 订单生成的机器IP
        contentMap.put("notify_url",notify_url); // 通知地址
        contentMap.put("trade_type",WeixinConfig.trade_type_jsapi); // 交易类型
//			WeixinUserInfo weixinUserInfo = (WeixinUserInfo) request.getSession().getAttribute("user");
        if(openId==null)
        {
            pageParamsMap.put("self_msg", "用户状态失效，请重新登陆"); 	
            return JSONObject.fromObject(pageParamsMap).toString();
        }
        contentMap.put("openid",openId); // 微信的用户标识
        String perpaySign= SignUtil.createSign("UTF-8", contentMap);
        contentMap.put("sign", perpaySign);
        String resultXML = WeixinUtil.httpRequestString(WeixinConfig.unified_order_url, "POST", getRequestXml(contentMap));
        if(resultXML==null)
        {
        	  pageParamsMap.put("self_msg", "无法链接微信服务器，请稍候重试"); 	
              return JSONObject.fromObject(pageParamsMap).toString();	
        }
			Map<String,String> map =doXMLParse(resultXML);
		       if(map.get("return_code").equals("SUCCESS"))
		       {
		    	   if(map.get("result_code").equals("SUCCESS"))
		    	   {
		    		   pageParamsMap.clear();
		    		   //prepay_id
		    		   pageParamsMap.put("appId", WeixinConfig.appId); 
		    		   pageParamsMap.put("timeStamp", JSSign.create_timestamp());
		    		   pageParamsMap.put("nonceStr", JSSign.createNoncestr());
		    		   pageParamsMap.put("package", "prepay_id="+map.get("prepay_id"));
		    		   pageParamsMap.put("signType", WeixinConfig.sign_type);
		    		   pageParamsMap.put("paySign", SignUtil.createSign("UTF-8", pageParamsMap));
		    		   pageParamsMap.put("sendSucUrl", sendSucUrl);//付款成功后跳转页面
		    		   pageParamsMap.put("sendFailUrl", sendFailUrl);//付款失败后跳转页面
		    		   //页面使用
		    		   pageParamsMap.put("packageValue", "prepay_id="+map.get("prepay_id")); 
		      	       pageParamsMap.put("self_result", "suc");
		    	       return JSONObject.fromObject(pageParamsMap).toString();	
		    		   
		    	   }
		    	   else
		    	   {
			    	   logger.error(String.format("统一下单成功，业务失败.错误代码%s,错误代码描述%s", map.get("err_code"), map.get("err_code_des")));
			    	   pageParamsMap.put("self_msg", map.get("err_code_des")); 	
			           return JSONObject.fromObject(pageParamsMap).toString();	
		    		   
		    	   }
		    	   
		       }
		       else
		       {
				   logger.error("统一下单失败:"+map.get("return_msg"));
		    	   pageParamsMap.put("self_msg", "下单失败，请稍候重试"); 	
		           return JSONObject.fromObject(pageParamsMap).toString();	
		    	
		       }
		} catch (Exception e) {
			logger.error("微信支付发生未知异常",e);
		 	   pageParamsMap.put("self_msg", "下单失败，请稍候重试"); 	
	           return JSONObject.fromObject(pageParamsMap).toString();	
		}
        
    }

	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)
					|| "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * @Description：返回给微信的参数
	 * @param return_code
	 *            返回编码
	 * @param return_msg
	 *            返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws JDOMException,
			IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();

		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}
	
	
	/**
	 * 判断是否来自微信
	 *
	 * @param request
	 * @return
	 */
	public static boolean isWeiXin(HttpServletRequest request) {
		String userAgent =request.getHeader("user-agent").toLowerCase();
		if (userAgent.indexOf("micromessenger") > 0) {// 是微信浏览器
		        return  true;
		      }
		return false;
	}

	/**
	 * 判断是否来自微信, 5.0 之后的支持微信支付
	 *
	 * @param request
	 * @return
	 */
	public static boolean isWeiXinPay(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isNotBlank(userAgent)) {
			Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
			Matcher m = p.matcher(userAgent);
			String version = null;
			if (m.find()) {
				version = m.group(1);
			}
			return (null != version && Integer.parseInt(version) >= 5);
		}
		return false;
	}

	/**
	 * 获取ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
