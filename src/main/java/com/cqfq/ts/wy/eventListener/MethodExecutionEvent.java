/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.eventListener;

import org.springframework.context.ApplicationEvent;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月1日 上午11:17:36
 */
public class MethodExecutionEvent extends ApplicationEvent{

    /**
     * 
     */
    private static final long serialVersionUID = 4537903310415013948L;
    
    private String methodName;
    
    private String methodExecutionStatus;

    /**
     * @param source
     */
    public MethodExecutionEvent(Object source) {
        super(source);
        // TODO Auto-generated constructor stub
    }
    public MethodExecutionEvent(Object source,String methodName,String methodExecutionStatus) {
        super(source);
        this.methodName = methodName;
        this.methodExecutionStatus = methodExecutionStatus;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodExecutionStatus() {
        return methodExecutionStatus;
    }

    public void setMethodExecutionStatus(String methodExecutionStatus) {
        this.methodExecutionStatus = methodExecutionStatus;
    }
    
}
