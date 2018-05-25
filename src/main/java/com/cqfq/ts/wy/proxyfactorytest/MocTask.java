/**
 * Copyright (C) 2018 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.proxyfactorytest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2018年1月26日 上午11:06:57
 */
public class MocTask implements Itask {
    
    public MocTask(){
        System.out.println("MocTask init...");
    }

    /* (non-Javadoc)
     * @see com.cqfq.ts.wy.proxyfactorytest.Itask#execute()
     */
    public void execute() {
        System.out.println("task executed");
    }
    
    @Test
    public void test() {
        MocTask task = new MocTask();
        ProxyFactory pf = new ProxyFactory(task);
//        pf.setProxyTargetClass(true);//设置为true 会采用CGLIB代理
        pf.setOptimize(true);//设置为true 会采用CGLIB代理
        pf.setInterfaces(Itask.class);
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("execute");
        advisor.setAdvice(new PerformanceMethodInterceptor());
        pf.addAdvisor(advisor);
        
        Itask proxyObject = (Itask)pf.getProxy();
        proxyObject.execute();
        System.out.println(pf.getProxy().getClass());
    }

}

class PerformanceMethodInterceptor implements MethodInterceptor {

    /* (non-Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch watch = new StopWatch();
        try {
            
            watch.start();
            return invocation.proceed();
        } finally {
            watch.stop();
            System.out.println(watch.toString());
        }
    }
    
}
