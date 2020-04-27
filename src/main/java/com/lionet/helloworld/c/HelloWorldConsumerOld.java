package com.lionet.helloworld.c;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Authror Lionet
 * @Date 2020/4/23 16:52
 * @Description 旧的 Api  简单队列， 消费者
 */

public class HelloWorldConsumerOld {

    private static final String HELLOWORDQUEUENAME = "hello_word_queue";

    public static void main(String[] args) throws Exception {

        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建连接通道
        Channel channel = null;

        channel = connection.createChannel();


        // 队列绑定通道
        //  QueueingConsumer consumer = new QueueingConsumer(channel);

        /**
         * @param queue  队列的名字
         *
         * @param autoAck 是否自动应答， 如果设置会自动应答后生产者发送给消费者后将在自己的存在中删除，
         *                如果设置会手动应答将在消费者回答生产者已经消费完消息了，生产者才会才会在内存中删除消息
         *                自动应答 ： true
         *                手动应答 ： false
         * @param consumerTag  连接中消费者标签，用来区分多个消费者
         *
         * @param noLocal 设置为true，表示 不能将同一个Conenction中生产者发送的消息传递给这个Connection中 的消费者
         *         在此通道的连接上发布的消息。注意，RabbitMQ服务器不支持此标记。
         *
         * @param exclusive 是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；二：该队列是否是私有的
         *
         * @param arguments  设置消费者一些参数 （后期研究）
         *
         * @param deliverCallback 当一个消息发送过来后的回调接口
         *
         * @param cancelCallback 当一个消费者取消订阅时的回调接口;取消消费者订阅队列时除了使用{@link Channel#basicCancel}之外的所有方式都会调用该回调方法
         *
         * @param shutdownSignalCallback  当channel/connection 关闭后回调
         *
         */
        // 老的 API 创建一个监听队列

        channel.basicConsume(HELLOWORDQUEUENAME, true, "", true, true, null, null, null, null);

        while (true) {

            /**
             *   QueueingConsumer内部其实是一个LinkBlockingQueue，
             *   它将从broker端接受到的信息先暂存到这个LinkBlockingQueue中，
             *   然后消费端程序在从这个LinkBlockingQueue中take出消息。
             *   试想一下，如果我们不take消息或者说take的非常慢，那么LinkBlockingQueue中的消息就会越来越多，最终造成内存溢出。
             */
            //     QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            //    String message = new String(delivery.getBody());

            //   System.out.println("message = " + message);
            //   break;
        }


/*
        // 创建 RabbitMQ 消息监听类， 监听 RPC 连接通道
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {


            }
        };*/


    }


}
