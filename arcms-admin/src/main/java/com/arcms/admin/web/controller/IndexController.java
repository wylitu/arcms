package com.arcms.admin.web.controller;

import com.suniusoft.security.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 *   
 *  @ProjectName: acrms 
 *  @Description: <p>
 *                     主页
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/10/30  
 */
@Controller
public class IndexController extends BaseController {

    /**
     * 返回页面
     */
    private static final String RETURN_PAGE = "index";

    @RequestMapping("/admin")
    public ModelAndView index(ModelMap modelMap, HttpSession session) {

        modelMap.put("indexFlag", "1");
        modelMap.put("user", getUser(session));

        return new ModelAndView(RETURN_PAGE, modelMap);

    }

}
