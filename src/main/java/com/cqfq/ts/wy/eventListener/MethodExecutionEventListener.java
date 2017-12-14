/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.eventListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月1日 上午11:29:29
 */
@Component
public class MethodExecutionEventListener implements ApplicationListener {

//    /**
//    * 处理方法开始执行的时候发布的MethodExecutionEvent事件 3
//    */
//    void onMethodBegin(MethodExecutionEvent evt);
//    /** * 处理方法执行将结束时候发布的MethodExecutionEvent事件 4
//    */
//    void onMethodEnd(MethodExecutionEvent evt);
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent arg0) {
        if (arg0 instanceof MethodExecutionEvent) {
            MethodExecutionEvent me = (MethodExecutionEvent) arg0;
            System.out.println("methodName:"+me.getMethodName()+"  methodExecutionStatus:"+me.getMethodExecutionStatus());
        }
    }
}
