package com.cqfq.ts.wy;

import java.lang.reflect.Method;

import org.junit.Test;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年11月14日 下午2:28:00
 */
public class JavassistTest {

    @Test
    public void test() throws NotFoundException {  
        // 获取默认类型池对象  
        ClassPool classPool = ClassPool.getDefault();  
      
        // 获取指定的类型  
        CtClass ctClass = classPool.get("java.lang.String");  
      
        System.out.println(ctClass.getName());  // 获取类名  
        System.out.println("\tpackage " + ctClass.getPackageName());    // 获取包名  
        System.out.print("\t" + Modifier.toString(ctClass.getModifiers()) + " class " + ctClass.getSimpleName());   // 获取限定符和简要类名  
        System.out.print(" extends " + ctClass.getSuperclass().getName());  // 获取超类  
        // 获取接口  
        if (ctClass.getInterfaces() != null) {  
            System.out.print(" implements ");     
            boolean first = true;  
            for (CtClass c : ctClass.getInterfaces()) {  
                if (first) {  
                    first = false;  
                } else {  
                    System.out.print(", ");  
                }  
                System.out.print(c.getName());  
            }  
        }  
        System.out.println();  
    }  
    
    @Test  
    public void modifyMethodTest() throws Exception {  
        // 获取本地类加载器  
        ClassLoader classLoader = null;  
        // 获取要修改的类  
        Class<?> clazz = classLoader.loadClass("edu.alvin.reflect.TestLib");  
      
        // 实例化类型池对象  
        ClassPool classPool = ClassPool.getDefault();  
        // 设置类搜索路径  
        classPool.appendClassPath(new ClassClassPath(clazz));  
        // 从类型池中读取指定类型  
        CtClass ctClass = classPool.get(clazz.getName());  
      
        // 获取String类型参数集合  
        CtClass[] paramTypes = {classPool.get(String.class.getName())};  
        // 获取指定方法名称  
        CtMethod method = ctClass.getDeclaredMethod("show", paramTypes);  
        // 赋值方法到新方法中  
        CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);  
        // 修改源方法名称  
        String oldName = method.getName() + "$Impl";  
        method.setName(oldName);  
      
        // 修改原方法  
        newMethod.setBody("{System.out.println(\"执行前\");" + oldName + "($$);System.out.println(\"执行后\");}");  
        // 将新方法添加到类中  
        ctClass.addMethod(newMethod);  
      
        // 加载重新编译的类  
        clazz = ctClass.toClass();      // 注意，这一行会将类冻结，无法在对字节码进行编辑  
        // 执行方法  
        clazz.getMethod("show", String.class).invoke(clazz.newInstance(), "hello");  
        ctClass.defrost();  // 解冻一个类，对应freeze方法  
    }  
    
    @Test
    public void test22() throws Exception {  
        ClassPool classPool = ClassPool.getDefault();  
      
        // 创建一个类  
        CtClass ctClass = classPool.makeClass("edu.alvin.reflect.DynamiClass");  
        // 为类型设置接口  
        //ctClass.setInterfaces(new CtClass[] {classPool.get(Runnable.class.getName())});  
      
        // 为类型设置字段  
        CtField field = new CtField(classPool.get(String.class.getName()), "value", ctClass);  
        field.setModifiers(Modifier.PRIVATE);  
        // 添加getter和setter方法  
        ctClass.addMethod(CtNewMethod.setter("setValue", field));  
        ctClass.addMethod(CtNewMethod.getter("getValue", field));  
        ctClass.addField(field);  
      
        // 为类设置构造器  
        // 无参构造器  
        CtConstructor constructor = new CtConstructor(null, ctClass);  
        constructor.setModifiers(Modifier.PUBLIC);  
        constructor.setBody("{}");  
        ctClass.addConstructor(constructor);  
        // 参数构造器  
        constructor = new CtConstructor(new CtClass[] {classPool.get(String.class.getName())}, ctClass);  
        constructor.setModifiers(Modifier.PUBLIC);  
        constructor.setBody("{this.value=$1;}");  
        ctClass.addConstructor(constructor);  
      
        // 为类设置方法  
        CtMethod method = new CtMethod(CtClass.voidType, "run", null, ctClass);  
        method.setModifiers(Modifier.PUBLIC);  
        method.setBody("{System.out.println(\"执行结果\" + this.value);}");  
        ctClass.addMethod(method);  
      
        // 加载和执行生成的类  
        Class<?> clazz = ctClass.toClass();  
        Object obj = clazz.newInstance();  
        clazz.getMethod("setValue", String.class).invoke(obj, "hello");  
        clazz.getMethod("run").invoke(obj);  
      
        obj = clazz.getConstructor(String.class).newInstance("OK");  
        clazz.getMethod("run").invoke(obj);  
    }
    
    /**
     * javassist动态代理 无需目标对象实现接口
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void proxyTest() throws InstantiationException, IllegalAccessException {
        //实例化代理类工厂  
        ProxyFactory factory = new ProxyFactory();
        
        //设置父类，proxyFactory将会动态生成一个类，继承该父类
        factory.setSuperclass(TestProxy.class);
        
        //设置过滤器，判断哪些方法调用需要被拦截
        factory.setFilter(new MethodFilter() {
            
            public boolean isHandled(Method arg0) {
                // TODO Auto-generated method stub
                return arg0.getName().startsWith("get");
            }
        });
        
        Class<?> clazz = factory.createClass();
        TestProxy proxy = (TestProxy)clazz.newInstance();
        
        ((ProxyObject)proxy).setHandler(new MethodHandler() {
            
            public Object invoke(Object arg0, Method arg1, Method arg2, Object[] arg3) throws Throwable {
                //拦截后前置处理，改写name属性的内容
                //实际情况可根据需求修改
                System.out.println(arg1.getName() + "被调用");
                Object ret = arg2.invoke(arg0, arg3);
                System.out.println("返回值: " + ret);  
                return ret;  
            }
        });
        
        proxy.setName("Alvin1");
        proxy.setValue("10001");
        proxy.getName();
        proxy.getValue();
    }
    
    
}


class TestProxy {  
    private String name;  
    private String value;  
      
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getValue() {  
        return value;  
    }  
    public void setValue(String value) {  
        this.value = value;  
    }  
}  
