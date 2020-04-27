package com.lionet.routing.c;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Authror Lionet
 * @Date 2020/4/26 23:58
 * @Description
 * 坑： 自己给自己写了一个坑， 同一个队列可以有多个路由key，手残一个队列绑定了两个路由key期间只会有一个消费者拿到消息
 * 想法：一个交换机向一个路由key发送消息， 但多个队列绑定了同一个路由key,是否可以实现？ 可以实现，因为交换机是根据 路由key进行转发到队列消息
 */

public class RoutingConsumer2 {

    private static final String ROUNTING_EXCHANGE_NAME="routing_exchange_name";


    private static final String ROUTING_EXCHANGE_ROUTING_KEY_1 = "routing_test_key_1";
    private static final String ROUTING_EXCHANGE_ROUTING_KEY_2 = "routing_test_key_2";


    private static final String ROUNTINOG_QUEUE_TEST_1 ="rountinog_queue_test_1";
    private static final String ROUNTINOG_QUEUE_TEST_2 ="rountinog_queue_test_2";

    public static void main(String[] args) throws Exception{

        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();
       // channel.basicQos(1);

        // 创建队列
        channel.queueDeclare(ROUNTINOG_QUEUE_TEST_2,false,false,false,null);

        // 队列与交换机与路由key绑定
        channel.queueBind(ROUNTINOG_QUEUE_TEST_2,ROUNTING_EXCHANGE_NAME,ROUTING_EXCHANGE_ROUTING_KEY_2,null);

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
