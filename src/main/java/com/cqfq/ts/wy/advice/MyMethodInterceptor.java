/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.advice;

import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月19日 下午2:40:42
 */
public class MyMethodInterceptor implements MethodInterceptor {

    /* (non-Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("method name:"+methodInvocation.getMethod().getName());
        System.out.println("method arguments:"+Arrays.toString(methodInvocation.getArguments()));
        System.out.println("Around method : before");
        
        try {
            
            Object result = methodInvocation.proceed();
            
            System.out.println("Around method : after");
            return result;
        } catch (IllegalArgumentException e) {
            System.out.println("Around method : throw  an  exception ");  
            throw  e;
        }
    }

    
}
