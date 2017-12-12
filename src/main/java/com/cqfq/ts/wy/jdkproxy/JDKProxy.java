package com.cqfq.ts.wy.jdkproxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>JDK动态代理 目标对象需实现接口 代理对象无需实现接口</p>
 * @author Administrator
 * @Date 2017年11月15日 上午10:08:31
 */
public class JDKProxy {

    @Test
    public void save() {
        IUserDao target = new UserDao();
        ((IUserDao)new ProxyFactroy(target).getProxyInstance()).save();
    }
}
/**
 * 
 * <p>代理类工厂</p>
 * @author Administrator
 * @Date 2017年11月15日 上午10:14:33
 */
class ProxyFactroy {
    private Object target;
    
    public ProxyFactroy(Object target){
        this.target = target;
    }
    
    public Object getProxyInstance() {
        
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("开始事务");
                Object proxyObject = method.invoke(target, args);
                System.out.println("提交事务");
                return proxyObject;
            }
        });
    }
}
interface IUserDao {
    void save();
}

class UserDao implements IUserDao{

    /* (non-Javadoc)
     * @see com.cqfq.ts.wy.jdkproxy.IUserDao#save()
     */
    public void save() {
        System.out.println("----已保存数据----");
        
    }

}
