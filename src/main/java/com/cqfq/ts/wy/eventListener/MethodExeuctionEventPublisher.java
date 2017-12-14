/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.eventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月1日 上午11:34:10
 */
@Component
public class MethodExeuctionEventPublisher implements ApplicationEventPublisherAware{

    @Autowired
    private MethodExecutionEventListener methodExecutionEventListener;
    
    private ApplicationEventPublisher eventPublisher;
    
    public void methodToMonitor() {
        MethodExecutionEvent startEvt = new MethodExecutionEvent(this, "methodToMonitor",MethodExecutionStatus.BEGIN);
        this.eventPublisher.publishEvent(startEvt);
        
        //执行实际方法逻辑
        MethodExecutionEvent endEvt = new MethodExecutionEvent(this, "methodToMonitor",MethodExecutionStatus.END);
        this.eventPublisher.publishEvent(endEvt);
        
        System.out.println("methodExecutionEventListener:"+methodExecutionEventListener);
    }
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
     */
    public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
        this.eventPublisher = arg0;
        
    }
    public void initMethod() {
        System.out.println("init method succ!");
    }
}
