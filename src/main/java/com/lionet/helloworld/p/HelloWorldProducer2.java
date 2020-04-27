package com.lionet.helloworld.p;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Authror Lionet
 * @Date 2020/4/23 16:43
 * @Description rabbitmq 简单模式：一个生产者，测试同时两个生产者在线
 */

public class HelloWorldProducer2 {

    private static final String HELLOWORDQUEUENAME = "hello_word_queue";

    public static void main(String[] args) throws Exception {

        /**
         * 1. 获取一个连接
         * 2. 创建一个通道
         * 3. 声明一个队列
         * 4. 发送消息
         */
        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建一个通道
        Channel channel = connection.createChannel();

        /**
         * @param queue 队列的名字
         *
         * @param durable 是否持久化, 队列的声明默认是存放到内存中的，
         *                如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，
         *                当rabbitmq重启之后会读取该数据库
         *
         * @param exclusive 是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；二：该队列是否是私有的
         *
         * @param autoDelete 是否自动删除队列，
         *                   当最后一个消费者断开连接之后队列是否自动被删除，
         *                   可以通过RabbitMQ Management，查看某个队列的消费者数量，当consumers = 0 时队列就会自动删除
         *
         * @param arguments 队列的其他属性， 没仔细研究，设置为 null
         */

        // 创建一个队列
        channel.queueDeclare(HELLOWORDQUEUENAME, false, false, false, null);


        /**
         * @param exchange 交换机， 用于消息转发
         *
         * @param routingKey  路由key， 路由key ，#匹配0个或多个单词，*匹配一个单词，在topic exchange做消息转发用
         *
         * @param mandatory 如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，
         *                  那么会调用basic.return方法将消息返还给生产者。false：出现上述情形broker会直接将消息扔掉
         *
         * @param immediate 如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者，那么这条消息不会放入队列中。
         *                  当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者。
         *
         *      注意，RabbitMQ服务器不支持此标记。
         *
         * @param props 需要注意的是 BasicProperties.deliveryMode，
         *              1:不持久化
         *              2:持久化 这里指的是消息的持久化，配合channel(durable=true),queue(durable)可以实现，即使服务器宕机，消息仍然保留
         *
         * @param body  消息体（建议发送 byte ）
         */

        String msg = "hello_word_queue_msg";

        for (int i = 0; i < 50; i++) {
            channel.basicPublish("", HELLOWORDQUEUENAME, null, msg.getBytes());
            Thread.sleep(100);
        }


        channel.close();
        connection.close();


        System.out.println("发送消息成功 msgBody: " + msg);

    }
}
