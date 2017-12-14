/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.eventListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月6日 下午2:41:43
 */
public class Test {

    @org.junit.Test
    public void test() {
        @SuppressWarnings("resource")
        ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/application-context.xml");
        MethodExeuctionEventPublisher mps = (MethodExeuctionEventPublisher)ac.getBean("methodExeuctionEventPublisher");
        mps.methodToMonitor();
    }
}
