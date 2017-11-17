package com.cqfq.ts.wy;

import java.lang.reflect.Method;

import org.junit.Test;

import aj.org.objectweb.asm.ClassVisitor;
import aj.org.objectweb.asm.ClassWriter;
import aj.org.objectweb.asm.MethodVisitor;
import aj.org.objectweb.asm.Opcodes;
import aj.org.objectweb.asm.Type;

/**
 * <p>字节码增强</p>
 * @Date 2017年11月14日 下午12:35:24
 */
public class ASMTest {

    @Test
    public void InternalNameTransform () {
        //获取JVM的内部名字
        System.out.println(Type.getInternalName(MyClass.class));
        //内部描述
        System.out.println(Type.getDescriptor(MyClass.class));
    }
    
    @Test
    public void generateClass() {
        ClassWriter classWriter = new ClassWriter(0);
//      Opcodes.V1_8; 指定类的版本
//      Opcodes.ACC_PUBLIC;表示这个类是public，
        //“org/victorzhzh/core/classes/MyClass”类的全限定名称    
        //第一个null位置变量定义的是泛型签名，    
        //“java/lang/Object”这个类的父类    
        //第二个null位子的变量定义的是这个类实现的接口   
        
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,Type.getInternalName(MyClass.class), null, Type.getInternalName(Object.class), null);
        
        ClassVisitor classVisitor = new MyClassVisitor(Opcodes.ACC_PUBLIC,classWriter);
        classVisitor.visitField(Opcodes.ACC_PRIVATE, "name", Type.getDescriptor(String.class), null, null);//定义name属性
        classVisitor.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()v", null, null).visitCode();//定义构造方法
        String setMethodDesc = "(" + Type.getDescriptor(String.class) + ")V";    
        classVisitor.visitMethod(Opcodes.ACC_PUBLIC, "setName", setMethodDesc, null, null).visitCode();//定义setName方法
        String getMethodDesc = "()" + Type.getDescriptor(String.class);    
        classVisitor.visitMethod(Opcodes.ACC_PUBLIC, "getName", getMethodDesc, null, null).visitCode();//定义getName方法
        
        byte[] classFile = classWriter.toByteArray();//生成字节码
        
        MyClassLoader classLoader = new MyClassLoader();//定义一个类加载器
        Class clazz = classLoader.defineClassFromClassFile("com.cqfq.ts.wy.MyClass", classFile);
        
        try {//利用反射方式，访问getName
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("getName");
                        System.out.println(obj.toString());
            System.out.println(method.invoke(obj, null));
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class MyClassLoader extends ClassLoader {
    public Class defineClassFromClassFile(String className, byte[] classFile)    
            throws ClassFormatError {    
        return defineClass(className, classFile, 0, classFile.length);    
    }
}

class MyClassVisitor extends ClassVisitor {

    /**
     * @param arg0
     * @param arg1
     */
    public MyClassVisitor(int arg0, ClassVisitor arg1) {
        super(arg0, arg1);
    }
    
    @Override    
    public MethodVisitor visitMethod(int access, String name, String desc,    
            String signature, String[] exceptions) {    
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc,    
                signature, exceptions);    
        if (name.equals("<init>")) {    
            return new InitMethodAdapter(0, methodVisitor);    
        } else if (name.equals("setName")) {    
            return new SetMethodAdapter(0,methodVisitor);    
        } else if (name.equals("getName")) {    
            return new GetMethodAdapter(0,methodVisitor);    
        } else {    
            return super.visitMethod(access, name, desc, signature, exceptions);    
        }    
    }  
}

//这个类生成具体的构造方法字节码    
class InitMethodAdapter extends MethodVisitor {    

    /**
     * @param arg0
     * @param arg1
     */
    public InitMethodAdapter(int arg0, MethodVisitor arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    @Override    
    public void visitCode() {    
        mv.visitVarInsn(Opcodes.ALOAD, 0);    
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object",    
                "<init>", "()V");//调用父类的构造方法    
        mv.visitVarInsn(Opcodes.ALOAD, 0);    
        mv.visitLdcInsn("zhangzhuo");//将常量池中的字符串常量加载刀栈顶    
        mv.visitFieldInsn(Opcodes.PUTFIELD,    
                "com/cqfq/ts/wy/MyClass", "name",    
                Type.getDescriptor(String.class));//对name属性赋值    
        mv.visitInsn(Opcodes.RETURN);//设置返回值    
        mv.visitMaxs(2, 1);//设置方法的栈和本地变量表的大小    
    }    
};

//这个类生成具体的setName方法字节码      
class SetMethodAdapter extends MethodVisitor {    

/**
     * @param arg0
     * @param arg1
     */
    public SetMethodAdapter(int arg0, MethodVisitor arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

@Override    
public void visitCode() {    
    mv.visitVarInsn(Opcodes.ALOAD, 0);    
    mv.visitVarInsn(Opcodes.ALOAD, 1);    
    mv.visitFieldInsn(Opcodes.PUTFIELD,    
            "com/cqfq/ts/wy/MyClass", "name",    
            Type.getDescriptor(String.class));    
    mv.visitInsn(Opcodes.RETURN);    
    mv.visitMaxs(2, 2);    
}    

} 

//这个类生成具体的getName方法字节    
class GetMethodAdapter extends MethodVisitor {    


/**
     * @param arg0
     * @param arg1
     */
    public GetMethodAdapter(int arg0, MethodVisitor arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

@Override    
public void visitCode() {    
    mv.visitVarInsn(Opcodes.ALOAD, 0);    
    mv.visitFieldInsn(Opcodes.GETFIELD,    
            "com/cqfq/ts/wy/MyClass", "name",    
            Type.getDescriptor(String.class));//获取name属性的值    
    mv.visitInsn(Opcodes.ARETURN);//返回一个引用，这里是String的引用即name    
    mv.visitMaxs(1, 1);    
}    
}   

