/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月15日 下午4:31:56
 */
public class PkeySpecifioQueryMethodPointcut extends DynamicMethodMatcherPointcut{

    /* (non-Javadoc)
     * @see org.springframework.aop.MethodMatcher#matches(java.lang.reflect.Method, java.lang.Class, java.lang.Object[])
     */
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        if (method.getName().startsWith("get") && targetClass.getPackage().getName().startsWith("...dao")) {
            if (!ArrayUtils.isEmpty(args)) {
                return StringUtils.equals("12345", args[0].toString());
            }
        }
        return false;
    }

}
