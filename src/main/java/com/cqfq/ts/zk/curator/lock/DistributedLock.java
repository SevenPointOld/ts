package com.cqfq.ts.zk.curator.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 分布式锁
 *
 * @author liujc [liujunchi@ifunq.com]
 * @date 2017-08-04 14:16
 **/
public class DistributedLock {

    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    private static CuratorFramework clientByFluent = CuratorFrameworkFactory.builder()
            .connectString("localhost:2181,localhost:2182,localhost:2183")
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(20000)
                .build();

    static {
        clientByFluent.start();
    }

    public static boolean fetch(String key) {
        return fetch(key, 1200L);
    }

    public static boolean fetch(String key, Long timeout) {
        try {
            key = "/" + key;
            String result = clientByFluent.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key);
            System.out.println("fetch lock success, path:" + result);
            return true;
        } catch (Exception e) {
            System.out.println("fetch lock fail ：" + e.getMessage());
            return false;
        }
    }

    public static void realease(String key) {

    }


    public static void main(String[] args) {
        System.out.println(fetch("test"));


    }
}
