package com.lionet.publishsubscribe.c;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author: Lionet
 * @Date 2020/4/26 22:04
 * @Description 此案例没有进行交换机绑定以及队列生命， 只是将消费者监听 consume2 队列
 *              在进行消费时有机会获取到 consumer2 的队列中的消息
 *              此案例只是个人疑惑, 在消費者中只关心监听的队列中是否会被推动消息
 * @Param:
 * @Return:
 */
public class PublishSubscribeConsumer4 {
    // 交换机昵称
    private static final String EXCHANGE_NAME = "exchange_test";
    private static final String EXCHANGE_QUEUE_NAME = "exchange_queue_consumer2";
    private static final String EXCHANGE_FANOUT_TYPE = "fanout";

    public static void main(String[] args) throws Exception {

        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();

       Consumer consumer =  new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                // 消息体
                String msgBody = new String(body);
                System.out.println("Consumer4: 消息体 ： " + msgBody);
            }
        };


       channel.basicConsume(EXCHANGE_QUEUE_NAME,true,consumer);


    }
}
