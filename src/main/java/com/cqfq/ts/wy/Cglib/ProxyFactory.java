/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.Cglib;

import java.lang.reflect.Method;

import org.junit.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * <p>Cglib动态代理</p>
 * @author Administrator
 * @Date 2017年11月15日 下午12:24:51
 */
public class ProxyFactory implements MethodInterceptor{
    
    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();
        
        //代理对象
        UserDao proxy = (UserDao)new ProxyFactory(target).getProxyInstance();
        
        //执行代理对象方法
        proxy.save();
    }
    @Test
    public void test() {
        //目标对象
        UserDao target = new UserDao();
        
        //代理对象
        UserDao proxy = (UserDao)new ProxyFactory(target).getProxyInstance();
        
        //执行代理对象方法
        proxy.save();
    }
    
    //维护目标对象
    private Object target;
    
    public ProxyFactory(Object target){
        this.target = target;
    }
    
    //获取目标对象的代理对象
    public Object getProxyInstance() {
        //1、工具类
        Enhancer en = new Enhancer();
        //2、设置父类
        en.setSuperclass(target.getClass());
        //3、设置回调函数
        en.setCallback(new CgLibCallback());;
        //4、创建子类(代理类)
        return en.create();
    }

    /* (non-Javadoc)
     * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
     */
    public Object intercept(Object arg0, Method method, Object[] arg2, MethodProxy arg3)
            throws Throwable {
        System.out.println("开始事务...");
        Object proxyObject = method.invoke(target, arg2);
        System.out.println("提交事务...");
        return null;
    }

}

class CgLibCallback implements MethodInterceptor{

    /* (non-Javadoc)
     * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
     */
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {
        System.out.println("Before invoke " + method);

        Object result = proxy.invokeSuper(obj, args);

        System.out.println("After invoke" + method);
        return result;
    }
    
}

class UserDao {

    public void save() {
        System.out.println("----已经保存数据Cglib!----");
    }
}
