package com.lionet.publishsubscribe.c;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.*;

import java.io.IOException;

public class PublishSubscribeConsumer {
    // 交换机昵称
    private static final String EXCHANGE_NAME = "exchange_test";
    private static final String EXCHANGE_QUEUE_NAME = "exchange_queue_consumer1";
    private static final String EXCHANGE_FANOUT_TYPE = "fanout";

    public static void main(String[] args) throws Exception {

        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();

        // 创建交换机　　
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_FANOUT_TYPE, false, false, false, null);

        // 声明队列
        channel.queueDeclare(EXCHANGE_QUEUE_NAME, false, false, false, null);

        /**
         * @param queue 队列名称
         * @param exchange 交换机名称
         * @param routingKey 路由key, 案例使用 fanout 模式是模式不存在路由key
         * @param arguments  额外参数
         */
        // 将队列和交换机绑定
        channel.queueBind(EXCHANGE_QUEUE_NAME, EXCHANGE_NAME, "", null);


       Consumer consumer =  new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                // 消息体
                String msgBody = new String(body);
                System.out.println("Consumer1： 消息体 ： " + msgBody);
            }
        };


       channel.basicConsume(EXCHANGE_QUEUE_NAME,true,consumer);


    }
}
