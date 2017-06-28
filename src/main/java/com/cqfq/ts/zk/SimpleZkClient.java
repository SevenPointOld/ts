package com.cqfq.ts.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 一个简单的zk客户端实现
 *
 * @author liujc
 * @create 2017-06-28 11:45
 **/
public class SimpleZkClient implements Watcher {

    private  CountDownLatch latch;

    public SimpleZkClient(CountDownLatch latch) {
        this.latch = latch;
    }

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        /**
         * 初始化客户端，这是只是发起连接请求就返回了，具体完成连接也就是建立会话是通过异步完成的
         * 这个通知会通过watcher回调到客户端
         */
        ZooKeeper zkClient = new ZooKeeper("localhost:2181,localhost:2182,localhost:2183", 5000,
                //这里注册一个watcher实例
                new SimpleZkClient(latch));
        //获取客户端的连接状态
        System.out.println("zk客户端状态：" + zkClient.getState());

        //阻塞等待，直到会话建立成功
        latch.await();

        System.out.println("zkClient session 创建成功");
        System.out.println("zk客户端状态：" + zkClient.getState());
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * watcher回调接口的处理方法
     *
     * @param watchedEvent
     */
    public void process(WatchedEvent watchedEvent) {
        //输入接收到事件通知
        System.out.println("接收到事件：" + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            latch.countDown();
        }
    }
}
