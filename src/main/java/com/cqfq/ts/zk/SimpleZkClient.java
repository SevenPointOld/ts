package com.cqfq.ts.zk;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 一个简单的zk客户端实现
 *
 * @author liujc
 * @create 2017-06-28 11:45
 **/
public class SimpleZkClient implements Watcher {

    public static ZooKeeper zkClient = null;

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
        zkClient = new ZooKeeper("localhost:2181,localhost:2182,localhost:2183", 5000,
                //这里注册一个watcher实例
                new SimpleZkClient(latch));
        //获取客户端的连接状态
        System.out.println("zk客户端状态：" + zkClient.getState());

        //阻塞等待，直到会话建立成功
        latch.await();

        //获取客户端的连接状态
        System.out.println("zk客户端状态：" + zkClient.getState());

        //获取客户端Id和秘钥
        long sesstionId = zkClient.getSessionId();
        byte[] sesstionPasswd = zkClient.getSessionPasswd();
        /**
         * 客户端会话创建 end
         */


        /**
         * 节点创建 begin
         */
        //同步创建数据节点
        String path = zkClient.create("/myNodeSyn",
                "hello zk!".getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                //只有临时节点，才允许创建子节点
                CreateMode.PERSISTENT);
        System.out.println("同步创建持久节点 path:" + path);
        //同步创建子节点，这里要注意，如果父节点是临时节点的话 是不允许创建子节点的
        String childPath = zkClient.create("/myNodeSyn/child",
                "hello zk!".getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("同步创建临时节点 path:" + childPath);


        //异步创建一个数据节点。
        zkClient.create("/myNodeAyn",
                "hello zk!".getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new SimpleZkClient.MyStringCallBack(),
                "我是上下文~~~~");
        /**
         * 节点创建 end
         */


        /**
         * 读取节点子节点列表 begin
         */
        //同步获取节点的一级子节点列表，并启用默认Watcher(就是实例化ZK客户端时的Watcher)
        List<String> childNodeList = zkClient.getChildren(path, true);
        System.out.println("同步获取子节点 " + childNodeList);
        //异步获取节点的一级列表，不注册Watcher

        //这里创建一个子节点，验证是否通知对应事件类型给客户端
        zkClient.create(path + "/child2",
                "hello zk!".getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        /**
         * 读取节点子节点列表 end
         */


        /**
         * 读取节点数据 begin
         */
        //同步读取节点数据


        /**
         * 读取节点数据 end
         */


        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * watcher回调接口的处理方法
     *
     * @param watchedEvent
     */
    public void process(WatchedEvent watchedEvent) {
        //输出接收到事件通知
        System.out.println("接收到事件：" + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (watchedEvent.getType() == Event.EventType.None && null == watchedEvent.getPath()) {
                //会话创建完成通知
                latch.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                //子节点列表变动通知
                try {
                    //重新同步获取子节点，并注册默认Watcher
                    List<String> childNodeList = zkClient.getChildren(watchedEvent.getPath(), true);
                    System.out.println("重新同步获取子节点 " + childNodeList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 回调对像
     */
    public static class MyStringCallBack implements AsyncCallback.StringCallback {

        /**
         *
         * @param rc    响应码 0：成功，-4：客户端和服务端连接已断开，-110：指定节点已存在，-112：会话已过期
         * @param path  客户端调用API传入的path
         * @param ctx   客户端调用API传入的上下文
         * @param name  ZK服务端响应的实际path（这是因为创建顺序节点的path客户端在节点创建成功之前是不知道的，ZK服务端为顺序节点的path加上了后缀）
         */
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("异步创建临时节点 rc=" + rc
                    + " path=" + path
                    + " ctx=" + ctx
                    + " name=" + name);
        }
    }
}
