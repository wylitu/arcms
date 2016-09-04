package com.suniusoft.pay.controller;

import com.suniusoft.pay.weixin.CoreService;
import com.suniusoft.pay.weixin.SignUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wangyong
 */
@Controller
public class WeixinController {


    @Autowired
    protected HttpServletRequest httpServletRequest;

    /**
     * 微信验证
     */
    @RequestMapping(value = "weixininit")
    public @ResponseBody String init(String signature, String timestamp,String nonce, String echostr,HttpServletRequest request) {

        String method = request.getMethod();
        if("GET".equals(method)){
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                return echostr;
            }
        }else {
            String respMessage = CoreService.processRequest(request);
            return respMessage==null?"":respMessage;
        }
        return "error";
    }
}
