package com.suniusoft.common.aspect;

import com.suniusoft.common.annotation.Domain;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;

import java.util.Date;
import java.util.Map;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: update切面
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/10 11:26  
 */
public class UpdateAspect {

    public void before(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {

            if (arg != null) {

                Class<?> cla = arg.getClass();

                Map<String, Object> loginInfo = SecurityContextHolder.getLoginInfo();

                String userId = "sys";

                if (loginInfo != null && loginInfo.get("userNo") != null) {
                    userId = String.valueOf(loginInfo.get("userNo"));
                }


                if (StringUtils.isNotBlank(cla.getSimpleName()) && cla.getSimpleName().indexOf("Example") != -1) {
                    continue;
                }

                if (cla.isAnnotationPresent(Domain.class)) {

                    try {

                        MethodUtils.invokeMethod(arg, "setGmtModified", new Date());
                        MethodUtils.invokeMethod(arg, "setModifiedBy",
                                StringUtils.isBlank(userId) ? "sys" : userId);

                    } catch (Exception e) {
                        throw new RuntimeException(joinPoint.toString() + "；参数" + cla.getName()
                                + "没有setModifiedBy,setGmtModified方法");
                    }

                } else {
                    throw new RuntimeException(joinPoint.toString() + "；参数"
                            + cla.getName() + "没有添加注解@Domain,请添加注解");
                }
            }
        }
    }


}
