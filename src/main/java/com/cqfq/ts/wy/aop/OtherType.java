/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aop;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年12月15日 下午3:45:30
 */
public class OtherType {
    private TargetObject target;
    
    public void nonCflowMethod() {
        target.method1();
    }
}
