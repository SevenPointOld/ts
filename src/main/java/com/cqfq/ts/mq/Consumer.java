package com.cqfq.ts.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liujc [liujunchi@ifunq.com]
 * @date 2017-09-05 13:37
 **/
public class Consumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("LJC_TEST_CCC");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.setMessageModel(MessageModel.CLUSTERING);

        consumer.subscribe("T2", "*");

        consumer.setNamesrvAddr("localhost:9876");

        consumer.registerMessageListener(
                new MessageListenerOrderly() {
                    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                        context.setAutoCommit(false);
                        Message msg = msgs.get(0);
                        System.out.println(msg.toString());
                        return ConsumeOrderlyStatus.COMMIT;
                    }
                }
        );

        consumer.start();
        System.out.println("消费端初始化成功！");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        System.out.printf("Consumer Started.%n");
    }
}
