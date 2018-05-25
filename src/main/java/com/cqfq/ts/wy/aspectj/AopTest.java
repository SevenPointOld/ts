/**
 * Copyright (C) 2018 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aspectj;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2018年2月7日 上午11:22:29
 */
public class AopTest {

  
    //目标对象有实现接口，spring会自动选择"jdk代理【动态代理】"
    //动态代理的标识：class com.sun.proxy.$Proxy10
    
    //目标对象未实现接口 spring会自动选择"Cglib代理【动态代理】"
    @Test
    public void test01() {
        @SuppressWarnings("resource")
        ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/application-aop.xml");
        UserDao dao = (UserDao)ac.getBean("userDao");
        System.out.println(dao.getClass());
        dao.save();
    }
}
