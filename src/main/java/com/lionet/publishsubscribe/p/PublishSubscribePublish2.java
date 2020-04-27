package com.lionet.publishsubscribe.p;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Authror Lionet
 * @Date 2020/4/25 22:41
 * @Description Publish/Subscribe 模式
 * 1. 生产者只需要声明交换机，将消息发送到交换机即可，再由交换机根据 type 发送到具体的队列
 * 2. 交换机没有存储消息的能力，只能进行转发消息，在没有队列绑定此交换机时，客户端向交换机发送的消息将会消息
 * 3. 特别注意，声明交换机时，durable参数设置持久化，而不是设置发送的消息设置为持久化，具体请看参数讲解
 */

public class PublishSubscribePublish2 {
    // 交换机昵称
    private static final String EXCHANGE_NAME = "exchange_test";
    private static final String EXCHANGE_BANDING_NAME = "exchange_banding_test";
    private static final String EXCHANGE_FANOUT_TYPE = "fanout";

    public static void main(String[] args) throws Exception {

        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();
        /**
         * @param exchange  交换机的昵称
         * @param type 交换机类型 ： direct，topic，headers，fanout
         * @param durable 如果我们声明持久交换，则为true（该交换将在服务器重启后保留下来）)
         * @param autoDelete 设置是否自动删除。autoDelete设置为true时，则表示自动删除。
         *                   自动删除的前提是至少有一个队列或者交换器与这个交换器绑定，之后，
         *                   所有与这个交换器绑定的队列或者交换器都与此解绑。
         *                   不能错误的理解—当与此交换器连接的客户端都断开连接时，RabbitMq会自动删除本交换器
         * @param internal 设置是否内置的。如果设置为true，
         *                 则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，
         *                 只能通过交换器路由到交换器这种方式。
         * @param arguments 用于交换的其他属性（构造参数）
         */
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_BANDING_NAME, EXCHANGE_FANOUT_TYPE, false, false, false, null);


        //　发送消息体
        String msg = "publish/subscribe 模式";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println(" publish/subscribe fanout 模式发送完毕");
        channel.close();
        connection.close();
    }

}
