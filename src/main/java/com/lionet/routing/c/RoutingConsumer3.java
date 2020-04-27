package com.lionet.routing.c;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Authror Lionet
 * @Date 2020/4/27 0:02
 * @Description  一个交换机根据一个 routing_key 发送消息， 不同队列绑定了 同一个 routing_key 都会接收到消息
 * 和 RoutingConsumer 对比
 */

public class RoutingConsumer3 {

    private static final String ROUNTING_EXCHANGE_NAME="routing_exchange_name";



    private static final String ROUTING_EXCHANGE_ROUTING_KEY_1 = "routing_test_key_1";



    private static final String ROUNTINOG_QUEUE_TEST_2 ="rountinog_queue_test_2";

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();
       // channel.basicQos(1);

        // 创建队列
        channel.queueDeclare(ROUNTINOG_QUEUE_TEST_2,false,false,false,null);

        // 队列与交换机与路由key绑定
        channel.queueBind(ROUNTINOG_QUEUE_TEST_2,ROUNTING_EXCHANGE_NAME,ROUTING_EXCHANGE_ROUTING_KEY_1,null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msgBody = new String(body);
                System.out.println("msgBody = " + msgBody);
            }
        };


        // 消费
        channel.basicConsume(ROUNTINOG_QUEUE_TEST_2,true,defaultConsumer);


    }
}
