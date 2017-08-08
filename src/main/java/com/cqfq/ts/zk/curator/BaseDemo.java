package com.cqfq.ts.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Curator 基础api演示
 *
 * @author liujc
 * @create 2017-07-04 11:27
 **/
public class BaseDemo {

    public static void main(String[] args) throws Exception {
        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //通过工厂方法获取客户端
//        CuratorFramework clientBystatic = CuratorFrameworkFactory.newClient("localhost:2181,localhost:2182,localhost:2183", retryPolicy);


        //使用Fluent风格 build一个会话。
        CuratorFramework clientByFluent = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181,localhost:2182,localhost:2183")
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(20000)
                .build();

        clientByFluent.start();
        System.out.println("会话创建成功");

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
