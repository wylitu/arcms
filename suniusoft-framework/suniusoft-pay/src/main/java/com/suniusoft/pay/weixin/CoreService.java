package com.suniusoft.pay.weixin;


import com.suniusoft.pay.weixin.message.resp.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

public class CoreService {
    /**
     * 处理微信发来的请求
     *
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 默认返回的文本消息内容
            String respContent = "欢迎访问爱卡";

            System.out.println("=========" + System.currentTimeMillis() + "=========" + requestMap);
            // 发送方帐号（open_id）
            String fromUserNo = requestMap.get("FromUserNo");
            // 公众帐号
            String toUserNo = requestMap.get("ToUserNo");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            String content = requestMap.get("Content");

            //先判断用户是否存在，如果存在更新位置信息，然后放入session

            //如果用户不存在修改session

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserNo(fromUserNo);
            textMessage.setFromUserNo(toUserNo);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
//				System.out.println(requestMap.get("Content"));
//				if(requestMap.get("Content").contains("6"))
//				{
//					respContent="您的信息已经收到，我们为贵公司和您一年共提供6次（90分钟）专业律师免费咨询，有问题可以留言或者电话/微信客服18500972680。";
//				}
//				else
//				{
//					respContent="发送6，即可获得专业律师免费咨询。";
//				}
				return null;
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "感谢关注，车管家 https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx93fa7f7554f27eec&redirect_uri="+ URLEncoder.encode("http://www.suniusoft.com/icard/mobile/pay/aiKaPayView")+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					return null;
				}
				// 位置信息
				else if (eventType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION.toUpperCase())) {
					try {
//						// 位置信息
//						String Latitude = requestMap.get("Latitude");
//						// 位置信息
//						String Longitude = requestMap.get("Longitude");
//						if(Latitude != null && Longitude!= null){
//							request.getSession().setAttribute(GlobalDefine.location, Latitude+","+Longitude);
//							//如果用户存在更新位置信息
//							User user = new User();
//							user.setLatitude(Double.valueOf(Latitude));
//							user.setLongitude(Double.valueOf(Longitude));
//							user.setOpenId(fromUserNo);
//							userMapper.updateByOpenId(user);
//							CarSteward carSteward = new CarSteward();
//							carSteward.setLatitude(Double.valueOf(Latitude));
//							carSteward.setLongitude(Double.valueOf(Longitude));
//							carSteward.setOpenId(fromUserNo);
//							carStewardMapper.updateByOpenId(carSteward);
//						}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                // 跳转
                else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
                    return null;
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    return null;
                }
            } else {
                return null;
            }

            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
}
