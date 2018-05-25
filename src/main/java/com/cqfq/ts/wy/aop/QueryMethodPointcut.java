/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aop;

import java.lang.reflect.Method;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月15日 下午4:25:58
 */
public class QueryMethodPointcut extends StaticMethodMatcherPointcut{

    /* (non-Javadoc)
     * @see org.springframework.aop.MethodMatcher#matches(java.lang.reflect.Method, java.lang.Class)
     */
    public boolean matches(Method method, Class<?> targetClass) {

        return method.getName().startsWith("get") && targetClass.getName().startsWith("...dao");
    }

}
