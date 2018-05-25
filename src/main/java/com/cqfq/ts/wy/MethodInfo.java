/**
 * Copyright (C) 2018 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2018年2月6日 上午10:08:17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodInfo {

    String author() default "absfree";
    String date();
    int version() default 1;
}
