package com.suniusoft.common.aspect;

import com.suniusoft.common.annotation.BizId;
import com.suniusoft.common.annotation.Domain;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

/**
 *  @ProjectName: arcms  
 *  @Description: insert切面
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/10 11:26  
 */
public class InsertAspect {

    public void before(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {

            if (arg != null) {
                Class<?> cla = arg.getClass();

                Map<String, Object> loginInfo = SecurityContextHolder.getLoginInfo();

                String userName = "sys";

                if (loginInfo != null && loginInfo.get("userName") != null) {
                    userName = String.valueOf(loginInfo.get("userName"));
                }

                if (cla.isAnnotationPresent(Domain.class)) {
                    try {

                        MethodUtils.invokeMethod(arg, "setGmtCreated", new Date());
                        MethodUtils.invokeMethod(arg, "setGmtModified", new Date());
                        MethodUtils.invokeMethod(arg, "setIsDeleted", false);
                        MethodUtils.invokeMethod(arg, "setCreatedBy",
                                StringUtils.isBlank(userName) ? "sys" : userName);
                        MethodUtils.invokeMethod(arg, "setModifiedBy",
                                StringUtils.isBlank(userName) ? "sys" : userName);

                    } catch (Exception e) {
                        throw new RuntimeException(joinPoint.toString()
                                + "；参数"
                                + cla.getName()
                                + "没有setGmtCreated或setGmtModified方法");
                    }



                    Field[] fields = cla.getDeclaredFields();

                    for (Field field : fields) {
                        if (field.isAnnotationPresent(BizId.class)) {
                            String fieldName = field.getName();
                            String getFiledName = "get"
                                    + StringUtils.capitalize(fieldName);
                            if (MethodUtils.invokeMethod(arg, getFiledName,
                                    null) == null) {
                                String setFiledName = "set"
                                        + StringUtils.capitalize(fieldName);
                                MethodUtils.invokeMethod(arg, setFiledName,
                                        System.currentTimeMillis() - 1434000000000l);// 设置业务Id
                            }

                            break;
                        }
                    }

                } else {
                    throw new RuntimeException(joinPoint.toString() + "；参数"
                            + cla.getName() + "没有添加注解@Domain,请添加注解");
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() - 1434000000000l);
    }

}
