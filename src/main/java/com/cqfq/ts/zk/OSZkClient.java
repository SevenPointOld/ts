package com.cqfq.ts.zk;

import com.google.gson.Gson;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 开源Zk客户端com.github.sgroschupf  弱爆了
 *  输出：
 *  ZkClient 会话创建成功！
     创建持久节点成功，path:/parent/child
     更新数据成功，path:/parent
     获取子节点列表，父节点path：/parent 当前子节点列表：[child]
     读取节点数据 path:/parent data:{name=Jack, age=30}
     节点发生变化 path:/parent data:{name=Jack Change !!, age=18 !}
     删除节点成功，path:/parent
     子节点列表变动，父节点path：/parent 当前子节点列表：null
     子节点列表变动，父节点path：/parent 当前子节点列表：null
     节点被删除 path:/parent

    为什么子节点列表表动会执行两次： 第一次时监听到由于递归删除子节点， 第二次是监听删除节点本身
 * @author liujc
 * @create 2017-07-03 17:00
 **/
public class OSZkClient {


    public static void main(String[] args) throws Exception{
        //创建会话
        ZkClient zkClient = new ZkClient("localhost:2181,localhost:2182,localhost:2183",
                5000,
                10000,
                new OSZkClient.JsonSerializer());
        System.out.println("ZkClient 会话创建成功！");

        //创建持久节点，并递归创建父节点
        String path = "/parent/child";
        zkClient.createPersistent(path, true);
        System.out.println("创建持久节点成功，path:" + path);



        String parentPath = "/parent";
        //更新数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Jack");
        map.put("age", "30");
        zkClient.writeData(parentPath, map);
        System.out.println("更新数据成功，path:" + parentPath);
        //读取子节点列表，并注册监听，且只需订阅一次，会一直接收到通知，不像Watcher每次通知后需要手动重新订阅
        //新增子节点、删除子节点、创建节点、删除节点 都会收到通知
        List<String> childs = zkClient.subscribeChildChanges(parentPath, new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("子节点列表变动，父节点path：" + parentPath + " 当前子节点列表：" + currentChilds);
            }
        });
        System.out.println("获取子节点列表，父节点path：" + parentPath + " 当前子节点列表：" + childs);


        //读取节点数据
        Object data = zkClient.readData(parentPath);
        System.out.println("读取节点数据 path:" + parentPath + " data:" + data );
        //注册节点变化事件监听
        zkClient.subscribeDataChanges(parentPath, new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点发生变化 path:" + dataPath + " data:" + data);
            }

            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("节点被删除 path:" + dataPath);
            }
        });

        //暂定三秒后，修改节点数据，看效果
        TimeUnit.SECONDS.sleep(3);
        map.put("name", "Jack Change !!");
        map.put("age", "18 !");
        zkClient.writeData(parentPath, map);

        //删除节点，递归删除节点下的所有子节点
        zkClient.deleteRecursive(parentPath);
        System.out.println("删除节点成功，path:" + parentPath);

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }


    /**
     * Json序列化器
     */
    public static class JsonSerializer implements ZkSerializer {

        private Gson gson = new Gson();

        public byte[] serialize(Object data) throws ZkMarshallingError {
            if (data == null) {
                return null;
            }
            return gson.toJson(data).getBytes();
        }

        public Object deserialize(byte[] bytes) throws ZkMarshallingError {
            if (bytes == null) {
                return null;
            }
            String str = new String(bytes);
            if ("".equals(str.trim())) {
                return new HashMap();
            }
            return gson.fromJson(str, HashMap.class);
        }
    }
}
