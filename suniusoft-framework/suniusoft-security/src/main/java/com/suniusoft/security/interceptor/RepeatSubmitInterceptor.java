package com.suniusoft.security.interceptor;

import com.suniusoft.common.aspect.SecurityContextHolder;
import com.suniusoft.security.annotation.PreventDuplicateSubmission;
import com.suniusoft.security.biz.service.token.SysTokenService;
import com.suniusoft.security.vo.SysTokenVO;
import org.apache.log4j.Logger;
import org.apache.struts.util.TokenProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 *  @ProjectName: sf-crm  
 *  @Description: 防止表单重复提交Interceptor
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/15  
 */
public class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(RepeatSubmitInterceptor.class);

    private static long THREE_MINUTES_MILLIS = 30 * 60 * 1000;

    @Autowired
    private SysTokenService sysTokenService;

    /**
     * 对于打@PreventDuplicateSubmission标注的请求处理
     * example: 1.@PreventDuplicateSubmission(needCreateToken = true) 创建token
     * 2.@PreventDuplicateSubmission(needValidateToken = true) 验证token
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = "";
        String userNo = "";

        Map<String, Object> loginInfo = SecurityContextHolder.getLoginInfo();
        if (!CollectionUtils.isEmpty(loginInfo)) {
            userNo = String.valueOf(loginInfo.get("userNo"));

        }

        try {

            if (handler instanceof ResourceHttpRequestHandler) {
                return true;
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            PreventDuplicateSubmission annotation = method.getAnnotation(PreventDuplicateSubmission.class);

            if (annotation != null) {


                boolean needCreateToken = annotation.needCreateToken();

                /**
                 * 创建token
                 */
                if (needCreateToken) {

                    token = TokenProcessor.getInstance().generateToken(request);
                    request.setAttribute("submit_token", token);
                    SysTokenVO sysToken = new SysTokenVO();
                    sysToken.setToken(token);
                    sysToken.setUserNo(userNo);

                    /**
                     * 失效时间30分钟
                     */
                    sysToken.setGmtExpired(new Date(System.currentTimeMillis() + THREE_MINUTES_MILLIS));

                    sysTokenService.createToken(sysToken);

                    logger.info("create submit_token [userNo:" + userNo +
                            ",submit_token:" + token + ",url:" + request.getServletPath() + "]");
                }

                boolean needValidateToken = annotation.needValidateToken();

                /**
                 * 验证token
                 */
                if (needValidateToken) {

                    token = request.getParameter("submit_token");
                    if (sysTokenService.isRepeatSubmit(token)) {
                        return true;
                    }

                    logger.error("please don't repeat submit, [userNo:" + userNo +
                            ",token:" + token + ",url:" + request.getServletPath() + "]");
                    throw new RuntimeException("表单已经提交，请不要重复提交!");
                }

            }

        } catch (Exception e) {

            logger.error("RepeatSubmitInterceptor.preHandle error , [userNo:" + userNo +
                    ",submit_token:" + token + ",url:" + request.getServletPath() + "]");

            throw new RuntimeException("表单已经提交，请不要重复提交!");

        }

        return true;
    }


}
