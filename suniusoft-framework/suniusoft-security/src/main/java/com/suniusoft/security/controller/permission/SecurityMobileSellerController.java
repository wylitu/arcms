package com.suniusoft.security.controller.permission;

import com.suniusoft.common.utils.Md5Encrypt;
import com.suniusoft.common.utils.StringUtils;
import com.suniusoft.common.utils.wxSign.Signature;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.controller.BaseController;
import com.suniusoft.security.interceptor.login.constant.SecurityConstant;
import com.suniusoft.security.interceptor.login.util.CookieUtils;
import com.suniusoft.security.vo.UserVO;
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
public class SecurityMobileSellerController extends BaseController {

    @Resource(name = "securityUserService")
    private SecurityUserService securityUserService;

    /**
     * 返回页面
     */
    private static final String RETURN_LOGIN_PAGE = "mobile/login/sellerLogin";

    /**
     * 返回商家注册页面
     */
    private static final String RETURN_REGISTER_PAGE = "mobile/seller/register";

    /**
     * 返回商家注册页面
     */
    private static final String RETURN_VALIDATE_PAGE = "mobile/seller/validate";


    @RequestMapping("/sellerLogin")
    public ModelAndView login(ModelMap modelMap) {
        return new ModelAndView(RETURN_LOGIN_PAGE, modelMap);
    }

    @RequestMapping("/sellerRegister")
    public ModelAndView sellerRegister(ModelMap modelMap) {
        return new ModelAndView(RETURN_REGISTER_PAGE, modelMap);
    }

    @RequestMapping("/sellerValidate")
    public ModelAndView sellerValidate(ModelMap modelMap) {
        return new ModelAndView(RETURN_VALIDATE_PAGE, modelMap);
    }

    @RequestMapping("/seller/updatePasswd")
    public ModelAndView updatePasswd(ModelMap modelMap) {
        return new ModelAndView("mobile/seller/updatePasswd", modelMap);
    }

    @RequestMapping(value = "/doSellerLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  loginAction(UserVO userVO, HttpSession session, HttpServletResponse response) {


        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (userVO == null) {

            map.put(ERROR_MESSAGE_KEY, "参数不能为空!");

            return map;

        }
        if (userVO == null || StringUtils.isBlank(userVO.getUserName())
                || StringUtils.isBlank(userVO.getPassword())) {

            map.put(ERROR_MESSAGE_KEY, "手机或密码不能为空!");

            return map;
        }

        UserVO user;

        if (StringUtils.isMobile(userVO.getUserName())) {

            user = securityUserService.findUserByMobile(userVO.getUserName());

        } else {

            user = securityUserService.findUserByUserName(userVO.getUserName());

        }


        String password = Md5Encrypt.md5(userVO.getPassword());

        if (user == null || !password.equals(user.getPassword())) {

            map.put(ERROR_MESSAGE_KEY, "手机号码或密码不正确!");

            return map;
        }

        if (!user.getIsSeller()) {

            map.put(ERROR_MESSAGE_KEY, "此账号非商家账号!");

            return map;
        }

        Cookie sellerCookie = new Cookie("_sun", user.getUserName());
        sellerCookie.setPath("/");
        sellerCookie.setMaxAge(365*24*60*60);
        CookieUtils.saveCookie(response, sellerCookie);
        session.setAttribute(SecurityConstant.SELLER_SESSION_KEY, user);

        map.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);

        return map;
    }

    @RequestMapping(value = "/seller/doUpdatePasswd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doUpdatePasswd(String oldPassword, String password,String sign,HttpSession session) {

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

    @RequestMapping("/sellerLoginOut")
    public String loginOut(HttpSession session,HttpServletRequest request, HttpServletResponse response) {

        session.setAttribute(SecurityConstant.SELLER_SESSION_KEY, null);
        CookieUtils.removeCookie(request,response,"_sun");
        return "redirect:/mobile/sellerLogin";

    }


}
