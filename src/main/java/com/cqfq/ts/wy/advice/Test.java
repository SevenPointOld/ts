/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.advice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月19日 下午3:12:31
 */
public class Test {

    @org.junit.Test
    public void test() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/application-context.xml");
        Book book =  (Book)ac.getBean("bookProxy");
        
        System.out.println("---------------------");  
        
        book.printName();  
          
        System.out.println("---------------------");  
          
        book.printUrl();  
          
        System.out.println("----------------------");  
        
    }
}
