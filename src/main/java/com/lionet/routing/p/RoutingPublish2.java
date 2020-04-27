package com.lionet.routing.p;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RoutingPublish2 {

    private static final String ROUNTING_EXCHANGE_NAME = "routing_exchange_name";


    private static final String ROUTING_EXCHANGE_ROUTING_KEY_1 = "routing_test_key_1";
    private static final String ROUTING_EXCHANGE_ROUTING_KEY_2 = "routing_test_key_2";


    private static final String EXCHANGE_TYPE = "direct";

    public static void main(String[] args) throws Exception {

        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 声明通道
        Channel channel = connection.createChannel();
       // channel.basicQos(1);

        // 声明交换机
        channel.exchangeDeclare(ROUNTING_EXCHANGE_NAME, EXCHANGE_TYPE, false, false, false, null);

        String msgBody = "routing_test_1";

        // 发送消息
        channel.basicPublish(ROUNTING_EXCHANGE_NAME,ROUTING_EXCHANGE_ROUTING_KEY_1,false,false,null,msgBody.getBytes());
        // 同一个连接生产者是否可以将一条消息是否可以通过多个路由key，转发到不同的队列中吗？ 是的， 可以
        channel.basicPublish(ROUNTING_EXCHANGE_NAME, ROUTING_EXCHANGE_ROUTING_KEY_2, false, null, msgBody.getBytes());

        System.out.println("发送完毕");
        channel.close();
        connection.close();
    }
}
