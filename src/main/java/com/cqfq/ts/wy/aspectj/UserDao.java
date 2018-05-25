/**
 * Copyright (C) 2018 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.aspectj;

import org.springframework.stereotype.Component;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2018年2月6日 下午4:28:00
 */
@Component
public class UserDao {

    /* (non-Javadoc)
     * @see com.cqfq.ts.wy.aspectj.IuserDao#save()
     */
    public void save() {
        System.out.println("..核心业务——核心业务..");
    }

}
