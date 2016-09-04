package com.suniusoft.security.controller.permission;

import com.google.common.collect.Maps;
import com.suniusoft.common.utils.*;
import com.suniusoft.common.utils.StringUtils;
import com.suniusoft.common.utils.wxSign.Signature;
import com.suniusoft.security.biz.domain.generation.sms.SmsCode;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.biz.service.sms.SmsService;
import com.suniusoft.security.biz.service.token.SysTokenService;
import com.suniusoft.security.constant.Constant;
import com.suniusoft.security.controller.BaseController;
import com.suniusoft.security.interceptor.login.constant.SecurityConstant;
import com.suniusoft.security.interceptor.login.util.CookieUtils;
import com.suniusoft.security.vo.SysTokenVO;
import com.suniusoft.security.vo.UserInfo;
import com.suniusoft.security.vo.UserVO;
import org.apache.commons.lang.*;
import org.apache.struts.util.TokenProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  @ProjectName: sf-crm  
 *  @Description: 用户登陆相关Controller
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/29  
 */
@Controller
@RequestMapping("/mobile")
public class SecurityMobileController extends BaseController {

    private static String smsSign = PropertyReader.getValue("sms.sign");

    @Resource(name = "securityUserService")
    private SecurityUserService securityUserService;

    @Resource(name = "sysTokenService")
    private SysTokenService sysTokenService;

    @Resource(name = "smsService")
    private SmsService smsService;

    private static long TOKEN_EXPIRED_TIME = 366 * 24 * 60 * 60 * 1000;

    /**
     * 返回页面
     */
    private static final String RETURN_PAGE = "mobile/login/login";

    private static String arcmsAppLoadUrl = PropertyReader.getValue("arcms.app.load.url");


    /**
     * 返回注册页面
     */
    private static final String RETURN_REGISTER_PAGE = "mobile/login/register";


    @RequestMapping("/login")
    public ModelAndView login(ModelMap modelMap) {
        return new ModelAndView(RETURN_PAGE, modelMap);
    }


    @RequestMapping("/user/updatePasswd")
    public ModelAndView updatePasswd(ModelMap modelMap) {
        return new ModelAndView("mobile/user/updatePasswd", modelMap);
    }

    @RequestMapping("/register")
    public ModelAndView register(String recommendUserId, String mobile, ModelMap modelMap) {

        modelMap.put("recommendUserId", recommendUserId);
        modelMap.put("mobile", mobile);
        modelMap.put("arcmsAppLoadUrl", arcmsAppLoadUrl);

        return new ModelAndView(RETURN_REGISTER_PAGE, modelMap);
    }

    @RequestMapping("/resetPasswd")
    public ModelAndView resetPasswd(ModelMap modelMap) {

        return new ModelAndView("mobile/login/resetPasswd", modelMap);
    }

    /**
     * 手机端登录
     *
     * @param userInfo
     * @param sign
     * @param request
     * @return
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginAction(UserInfo userInfo, String sign, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        HttpSession session = request.getSession();

        if (userInfo == null) {

            map.put(ERROR_MESSAGE_KEY, "参数不能为空!");

            return map;

        }

        try {

            String sig = Signature.getSign(userInfo);

            if (StringUtils.isBlank(sig) || !sig.equals(sign)) {

                map.put(ERROR_MESSAGE_KEY, "签名出错！");

                return map;

            }

        } catch (IllegalAccessException e) {

            map.put(ERROR_MESSAGE_KEY, "签名出错！");

            return map;

        }

        String token = TokenProcessor.getInstance().generateToken(request);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("token", token);

        SysTokenVO sysTokenVO = new SysTokenVO();
        sysTokenVO.setToken(token);
        sysTokenVO.setType("login");

        /**
         * 失效时间24小时
         */
        sysTokenVO.setGmtExpired(new Date(System.currentTimeMillis() + TOKEN_EXPIRED_TIME));
        resultMap.put("expiredTime", TOKEN_EXPIRED_TIME);

        UserVO userVO = (UserVO) BeanCopierUtils.copyProperties(userInfo, UserVO.class);

        if (Constant.LoginType.AUTH.equals(userInfo.getLoginType())) {

            if (StringUtils.isBlank(userInfo.getUserNo())) {

                map.put(ERROR_MESSAGE_KEY, "用户名不能为空!");

                return map;

            }

            if (StringUtils.isBlank(userInfo.getWxOpenId()) && StringUtils.isBlank(userInfo.getQqOpenId())) {

                map.put(ERROR_MESSAGE_KEY, "wxOpenId 或者 qqOpenId 不能为空!");

                return map;

            }

            try {
                userVO.setPassword(Md5Encrypt.md5("123456"));
                securityUserService.saveUserInfo(userVO);

                sysTokenVO.setUserId(userVO.getUserId());
                sysTokenVO.setUserNo(userVO.getUserNo());

                sysTokenService.createToken(sysTokenVO);
            } catch (Exception e) {
                map.put(ERROR_MESSAGE_KEY, e.getMessage());
            }

            Cookie sellerCookie = new Cookie("_u", userVO.getUserNo());
            sellerCookie.setPath("/");
            sellerCookie.setMaxAge(365 * 24 * 60 * 60);
            CookieUtils.saveCookie(response, sellerCookie);
            session.setAttribute(SecurityConstant.SESSION_KEY, userVO);

            map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
            map.put(INFO_KEY, resultMap);

            return map;
        }

        if (Constant.LoginType.COMMON.equals(userInfo.getLoginType())) {

            if (!StringUtils.isMobile(userInfo.getMobile())) {

                map.put(ERROR_MESSAGE_KEY, "手机号码为空或非法!");

                return map;

            }

            UserVO user = securityUserService.findUserByMobile(userVO.getMobile());

            if (user == null) {
                map.put(ERROR_MESSAGE_KEY, "用户不存在!");

                return map;
            }

            String password = user.getPassword();

            if (password.equals(userInfo.getPassword())) {

                sysTokenVO.setUserId(user.getUserId());
                sysTokenVO.setUserNo(user.getUserNo());
                sysTokenService.createToken(sysTokenVO);

                Cookie sellerCookie = new Cookie("_u", userVO.getUserNo());
                sellerCookie.setPath("/");
                sellerCookie.setMaxAge(365 * 24 * 60 * 60);
                CookieUtils.saveCookie(response, sellerCookie);
                session.setAttribute(SecurityConstant.SESSION_KEY, user);

                map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
                map.put(INFO_KEY, resultMap);

                return map;

            }

            map.put(ERROR_MESSAGE_KEY, "密码不正确!");

            return map;
        }

        map.put(ERROR_MESSAGE_KEY, "登录类型非法!");

        return map;

    }

    /**
     * 手机验证
     *
     * @param mobile
     * @param smsCode
     * @param sign
     * @return
     */
    @RequestMapping(value = "/doValidate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doValidate(String mobile,
                                          String sign, String smsCode) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (!StringUtils.isMobile(mobile)) {
            map.put(ERROR_MESSAGE_KEY, "手机号码不能为空或非法！");
            return map;
        }

        if (StringUtils.isBlank(smsCode)) {

            map.put(ERROR_MESSAGE_KEY, "验证码不能为空！");

            return map;
        }

        Map<String, Object> mapSign = new HashMap<String, Object>();

        mapSign.put("mobile", mobile);
        mapSign.put("smsCode", smsCode);
        String sig = Signature.getSign(mapSign);

        if (StringUtils.isBlank(sig) || !sig.equals(sign)) {

            map.put(ERROR_MESSAGE_KEY, "签名出错！");

            return map;

        }

        if (!smsService.validateCode(mobile, smsCode)) {

            map.put(ERROR_MESSAGE_KEY, "验证码不正确！");

            return map;
        }

        map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);

        map.put(INFO_KEY, "验证成功");

        return map;

    }

    /**
     * 手机端用户注册
     *
     * @param mobile
     * @param password
     * @param recommendUserId
     * @param sign
     * @param request
     * @return
     */
    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(String mobile, String password, String recommendUserId,
                                          String sign, HttpServletRequest request, HttpSession session) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
            map.put(ERROR_MESSAGE_KEY, "手机号码或密码不能为空！");
            return map;
        }

        if (!StringUtils.isBlank(recommendUserId)) {

            if (!StringUtils.isNumeric(recommendUserId)) {
                map.put(ERROR_MESSAGE_KEY, "推荐码格式不正确！");
                return map;
            }

            UserVO userVO = securityUserService.findUserByUserId(Long.parseLong(recommendUserId));
            if (userVO == null) {
                map.put(ERROR_MESSAGE_KEY, "推荐码不存在！");
                return map;
            }

        }

        Map<String, Object> mapSign = new HashMap<String, Object>();

        mapSign.put("mobile", mobile);
        mapSign.put("password", password);
        mapSign.put("recommendUserId", StringUtils.isBlank(recommendUserId) ? "" : recommendUserId);

        String sig = Signature.getSign(mapSign);

        if (StringUtils.isBlank(sig) || !sig.equals(sign)) {

            map.put(ERROR_MESSAGE_KEY, "签名出错！");

            return map;

        }

        String token = TokenProcessor.getInstance().generateToken(request);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("token", token);

        SysTokenVO sysTokenVO = new SysTokenVO();
        sysTokenVO.setToken(token);
        sysTokenVO.setType("login");
        /**
         * 失效时间24小时
         */
        sysTokenVO.setGmtExpired(new Date(System.currentTimeMillis() + TOKEN_EXPIRED_TIME));
        resultMap.put("expiredTime", TOKEN_EXPIRED_TIME);

        UserVO user = securityUserService.findUserByMobile(mobile);

        if (user != null) {

            map.put(ERROR_CODE_KEY, -2);
            map.put(ERROR_MESSAGE_KEY, "该号码已注册，请直接登陆！");

            return map;

        }

        try {

            UserVO userVO = new UserVO();
            userVO.setMobile(mobile);
            userVO.setPassword(password);
            Long code = IdGenUtils.idGen();
            userVO.setUserNo("arcms" + code);
            securityUserService.saveUserInfo(userVO);

            sysTokenVO.setUserId(userVO.getUserId());
            sysTokenVO.setUserNo(userVO.getUserNo());
            sysTokenService.createToken(sysTokenVO);

            session.setAttribute(SecurityConstant.SESSION_KEY, userVO);

        } catch (Exception e) {

            map.put(ERROR_MESSAGE_KEY, e.getMessage());

            return map;

        }

        map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
        map.put(INFO_KEY, resultMap);

        return map;

    }

    @RequestMapping(value = "/user/doUpdatePasswd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doUpdatePasswd(String oldPassword, String password, String sign,HttpSession session) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        UserVO userVO = getUser(session);

        if (StringUtils.isBlank(password)) {
            map.put(ERROR_MESSAGE_KEY, "新密码为空!");
            return map;
        }


        Map<String, Object> mapSign = new HashMap<String, Object>();

        mapSign.put("oldPassword", oldPassword);
        mapSign.put("password", password);

        String sig = Signature.getSign(mapSign);

        if (StringUtils.isBlank(sig) || !sig.equals(sign)) {

            map.put(ERROR_MESSAGE_KEY, "签名出错！");

            return map;

        }


        try {

            userVO.setPassword(oldPassword);
            userVO.setNewpasswd1(password);
            securityUserService.updatePasswd(userVO);

        } catch (Exception e) {

            map.put(ERROR_MESSAGE_KEY, e.getMessage());

            return map;

        }

        map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
        map.put(INFO_KEY, "密码修改成功!");

        return map;

    }

    @RequestMapping(value = "/doResetPasswd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doResetPasswd(String mobile, String smsCode, String password, String sign) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (StringUtils.isBlank(password)) {
            map.put(ERROR_MESSAGE_KEY, "新密码为空!");
            return map;
        }


        Map<String, Object> mapSign = new HashMap<String, Object>();

        mapSign.put("mobile", mobile);
        mapSign.put("smsCode", smsCode);
        mapSign.put("password", password);

        String sig = Signature.getSign(mapSign);

        if (StringUtils.isBlank(sig) || !sig.equals(sign)) {

            map.put(ERROR_MESSAGE_KEY, "签名出错！");

            return map;
        }

        try {

            if (!smsService.validateCode(mobile, smsCode)) {

                map.put(ERROR_MESSAGE_KEY, "验证码不正确！");

                return map;
            }
            UserVO userVO = new UserVO();
            userVO.setPassword(password);
            userVO.setMobile(mobile);
            securityUserService.updateUserByMobile(userVO);

        } catch (Exception e) {

            map.put(ERROR_MESSAGE_KEY, e.getMessage());

            return map;

        }

        map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
        map.put(INFO_KEY, "密码重置成功!");

        return map;

    }

    @RequestMapping(value = "/smsCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> smsCode(String mobile, String validateRepeat, String sign) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (!StringUtils.isMobile(mobile)) {
            map.put(ERROR_MESSAGE_KEY, "手机号码为空或非法!");
            return map;
        }

        Map<String, Object> mapSign = new HashMap<String, Object>();

        mapSign.put("mobile", mobile);
        boolean validateRepeatFlag=false;
        if (!StringUtils.isBlank(validateRepeat)) {
            validateRepeatFlag= Boolean.parseBoolean(validateRepeat);
            mapSign.put("validateRepeat", validateRepeat);
        }

        String sig = Signature.getSign(mapSign);
        if (StringUtils.isBlank(sig) || !sig.equals(sign)) {
            map.put(ERROR_MESSAGE_KEY, "签名出错！");
            return map;
        }

        if (validateRepeatFlag) {
            UserVO userVO = securityUserService.findUserByMobile(mobile);
            if (userVO!=null) {
                map.put(ERROR_MESSAGE_KEY, "该手机号已经被绑定!");
                return map;
            }
        }

        String code = String.valueOf(Math.random() * 9000 + 1000);
        code = code.substring(0, 4);
        String smsSendResult;

        try {

            smsSendResult = SMSUtils.sendSmsVerifyCode(mobile, "【爱卡玉兔】尊敬的客户，您的验证码为：" + code + ",请尽快输入。");

        } catch (Exception e) {

            map.put(ERROR_MESSAGE_KEY, e.getMessage());

            return map;

        }

        if ("0".equals(smsSendResult)) {
            SmsCode smsCode = new SmsCode();
            smsCode.setCode(code);
            smsCode.setMobile(mobile);
            smsCode.setGmtExpired(new Date(System.currentTimeMillis() + 3 * 60 * 1000));
            smsService.saveSmsCode(smsCode);

            map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
            Map<String, Object> smsMap = Maps.newHashMap();
            smsMap.put("smsCode", code);
            map.put(INFO_KEY, smsMap);
            return map;
        }

        map.put(ERROR_MESSAGE_KEY, "亲,短信发送失败,请稍后重试!");

        return map;

    }

    @RequestMapping("/loginOut")
    @ResponseBody
    public Map<String, Object> loginOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {

            session.setAttribute(SecurityConstant.SESSION_KEY, null);
            CookieUtils.removeCookie(request, response, "_s");

        } catch (Exception e) {
            map.put(ERROR_CODE_KEY, ErrorCode.ERROR);
            map.put(ERROR_MESSAGE_KEY, e.getMessage());
            return map;
        }

        map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);

        return map;
    }

}
