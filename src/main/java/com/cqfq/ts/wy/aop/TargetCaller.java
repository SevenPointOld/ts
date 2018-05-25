/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aop;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月15日 下午3:42:06
 */
public class TargetCaller {

    private TargetObject target;
    
    public void callMethod() {
        target.method1();
    }

    public void setTarget(TargetObject target) {
        this.target = target;
    }
    
    
}
