/**
 * Copyright (C) 2018 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aspectj;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2018年2月6日 下午4:23:55
 */
@Component //加入IOC容器
@Aspect//指定当前类为切面类
public class Aop {
    
    //指定切入点表达式,拦截哪些方法,即为哪些类生成代理对象
    //@Pointcut(" execution(* com.cqfq.ts.wy.aspectj.save(..))") ..代表所有参数
    //@Pointcut(" execution(* com.cqfq.ts.wy.aspectj.*(..)") 指所有的方法
    //@Pointcut(" execution( * com.cqfq.ts.wy.aspectj.save())") 指定save方法
    
    @Pointcut("execution(* com.cqfq.ts.wy.aspectj.UserDao.*(..))")
    public void pointCut() {
        
    }
    
    @Before("pointCut()")
    public void begin() {
        System.out.println("开启事务");
    }
    
    @After("pointCut()")
    public void close() {
        System.out.println("关闭事务");
    }

}
