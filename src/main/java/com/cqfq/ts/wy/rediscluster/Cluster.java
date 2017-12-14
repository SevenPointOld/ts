/**
 * Copyright (C) 2017 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.rediscluster;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.JedisClusterCRC16;

/**
 * <p>类描述</p>
 * @author Administrator
 * @Date 2017年11月27日 下午4:52:20
 */
public class Cluster {

    @Test
    public void cluster() {
        String key = "1417";
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7000));
        
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        jc.setnx(key, "bar");
        String value = jc.get(key);
        System.out.println("key-"+key+" slot-"+JedisClusterCRC16.getCRC16(key)+" value-"+value);
        
        String key2 = "288";
        jc.setnx(key2, "bar2");
        String value2 = jc.get(key2);
        System.out.println("key2-"+key2+" slot-"+JedisClusterCRC16.getCRC16(key2)+" value-"+value2);
    }
}
