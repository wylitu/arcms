package com.suniusoft.security.controller.permission;

import com.suniusoft.common.utils.Md5Encrypt;
import com.suniusoft.common.utils.StringUtils;
import com.suniusoft.security.biz.service.permission.SecurityUserService;
import com.suniusoft.security.controller.BaseController;
import com.suniusoft.security.interceptor.login.constant.SecurityConstant;
import com.suniusoft.security.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
public class SecurityController extends BaseController {


    @Resource(name = "securityUserService")
    private SecurityUserService securityUserService;
    /**
     * 返回页面
     */
    private static final String RETURN_PAGE = "permission/login";

    @RequestMapping("/login")
    public ModelAndView login(ModelMap modelMap) {
        return new ModelAndView(RETURN_PAGE, modelMap);
    }

    @RequestMapping("/doLogin")
    public String loginAction(UserVO userVO, HttpSession session) {

        if (userVO == null || StringUtils.isBlank(userVO.getUserName())
                || StringUtils.isBlank(userVO.getPassword())) {
            return "redirect:/login";
        }

        UserVO user;

        if(StringUtils.isMobile(userVO.getUserName())){

            user = securityUserService.findUserByMobile(userVO.getUserName());

        }else{

            user = securityUserService.findUserByUserName(userVO.getUserName());

        }


        String password = Md5Encrypt.md5(userVO.getPassword());

        if (user != null && password.equals(user.getPassword())) {
            session.setAttribute(SecurityConstant.SESSION_KEY, user);
            return "redirect:/";
        }

        return "redirect:/login";

    }

    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session) {

        session.setAttribute(SecurityConstant.SESSION_KEY, null);

        return "redirect:/login";

    }

    @RequestMapping("/passwd")
    public
    @ResponseBody
    Map<String, String> passwd(UserVO userVO, BindingResult result) {

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put(ERROR_CODE_KEY, ErrorCode.ERROR);

        if (StringUtils.isBlank(userVO.getUserName())) {
            returnMap.put(ERROR_MESSAGE_KEY, "缺少用户名!");
            return returnMap;
        }

        if (StringUtils.isBlank(userVO.getPassword())) {
            returnMap.put(ERROR_MESSAGE_KEY, "原密码不能为空!");
            return returnMap;
        }

        if (StringUtils.isBlank(userVO.getNewpasswd1()) || StringUtils.isBlank(userVO.getNewpasswd2())) {
            returnMap.put(ERROR_MESSAGE_KEY, "新密码不能为空!");
            return returnMap;
        }

        if (!userVO.getNewpasswd1().equals(userVO.getNewpasswd2())) {
            returnMap.put(ERROR_MESSAGE_KEY, "两次输入密码不一致!");
            return returnMap;
        }

        if (result.hasErrors()) {
            returnMap.put(ERROR_MESSAGE_KEY, getError(result));
            return returnMap;
        }

        try {
            userVO.setNewpasswd1(Md5Encrypt.md5(userVO.getNewpasswd1()));
            userVO.setModifiedBy(getUserName());
            userVO.setPassword(Md5Encrypt.md5(userVO.getPassword()));
            boolean flag = securityUserService.updatePasswd(userVO);
            if (!flag) {
                returnMap.put(ERROR_MESSAGE_KEY, "修改密码失败");
                return returnMap;
            }
        } catch (Exception e) {
            returnMap.put(ERROR_MESSAGE_KEY, e.getMessage());
            return returnMap;
        }

        returnMap.put(ERROR_CODE_KEY, ErrorCode.SUCCESS);
        returnMap.put(INFO_KEY, "密码修改成功!");

        return returnMap;

    }
}
