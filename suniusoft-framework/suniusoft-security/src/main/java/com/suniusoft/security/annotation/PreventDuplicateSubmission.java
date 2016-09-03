package com.suniusoft.security.annotation;

import java.lang.annotation.*;

/**
 *   
 *  @ProjectName: suniu 
 *  @Description:  <p>
 *                    防止重复提交注解，用于方法上<br/>
 *                    在新建页面方法上，设置needCreateToken()为true，此时拦截器会在DB和request中保存一个token，
 *                    同时需要在新建的页面中添加
 *                    <input type="hidden" name="token" value="${token}">
 *                    <br/>
 *                    保存方法需要验证重复提交的，设置needValidateToken为true
 *                    此时会在拦截器中验证是否重复提交
 *                   </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/5/15  
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventDuplicateSubmission {
    boolean needCreateToken() default false;
    boolean needValidateToken() default false;
}
