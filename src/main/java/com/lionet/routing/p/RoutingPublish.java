package com.lionet.routing.p;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


/**
 * @Authror Lionet
 * @Date 2020/4/26 23:19
 * @Description
 * 1.获取连接
 * 2. 声明通道
 * 3. 生命交换机
 * 4. 将消息发送到交换机中， 主要需要设置此消息输入那个路由key, 交换机会根据路由key进行分发消息
 * 5. 一个交换机将消息发送到
 */

public class RoutingPublish {

    private static final String ROUNTING_EXCHANGE_NAME = "routing_exchange_name";


    private static final String ROUTING_EXCHANGE_ROUTING_KEY_1 = "routing_test_key_1";


    private static final String EXCHANGE_TYPE = "direct";

    public static void main(String[] args) throws Exception {

        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 声明通道
        Channel channel = connection.createChannel();
      //  channel.basicQos(1);

        // 声明交换机
        channel.exchangeDeclare(ROUNTING_EXCHANGE_NAME, EXCHANGE_TYPE, false, false, false, null);


        // 创建消息体
        String msgBody = "routing_test_1";

        // 创建生产者， 将交换机以及routing绑定
        channel.basicPublish(ROUNTING_EXCHANGE_NAME, ROUTING_EXCHANGE_ROUTING_KEY_1, false, null, msgBody.getBytes());

        System.out.println("发送完毕");

        channel.close();
    }
}
