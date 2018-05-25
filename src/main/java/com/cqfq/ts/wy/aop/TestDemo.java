/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aop;

import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import com.cqfq.ts.wy.Cglib.ProxyFactory;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月15日 下午3:49:14
 */
public class TestDemo {

    @Test
    private void test() {
        ControlFlowPointcut pointcut = new ControlFlowPointcut(TargetCaller.class,"callMethod");
        Advice advice = new SimpLeAdvise();
        Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
        
        ProxyFactory pf = new ProxyFactory(advisor);
        TargetObject targetOnject = (TargetObject)pf.getProxyInstance();
        targetOnject.method1();
        
    }
}
