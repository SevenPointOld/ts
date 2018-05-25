package com.cqfq.ts.wy;

import java.lang.reflect.Method;

public class Test {
	
	public static void main(String[] args) {
		Class<AnnotationTest> cls = AnnotationTest.class;
		for (Method method : cls.getMethods()) {
		    MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
		    if (methodInfo != null) {
                System.out.println("name:"+ method.getName());
                System.out.println("author:"+ methodInfo.author());
                System.out.println("author:"+ methodInfo.date());
                System.out.println("author:"+ methodInfo.version());
            }
        }
	}

}
