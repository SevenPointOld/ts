/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月15日 下午4:08:18
 */
public class SimpLeAdvise implements MethodInterceptor{

    /* (non-Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation arg0) throws Throwable {
        System.out.println("before");
        Object retVal = arg0.proceed();
        System.out.println("before");
        return retVal;
    }

}
