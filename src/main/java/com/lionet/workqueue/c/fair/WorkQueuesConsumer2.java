package com.lionet.workqueue.c.fair;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Authror Lionet
 * @Date 2020/4/23 16:52
 * @Description 新的 API 简单队列， 消费者
 */

public class WorkQueuesConsumer2 {

    private static final String HELLOWORDQUEUENAME = "Work_Queue_Test";


    private static final String UTF_8 = "utf-8";


    public static void main(String[] args) throws Exception {

        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建连接通道
        final Channel channel = connection.createChannel();



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
        // 声明队列
        channel.queueDeclare(HELLOWORDQUEUENAME, false, false, false, null);


        /**
         *@param prefetchSize 可接收消息的大小
         * @param prefetchCount 每个消费者在发送确认应答消息之前，每次只发送设置的消息数
         * @param global  false/true 以上两个设置 true 为整个 connection; 如果是false则说明只是针对于这个Channel的。
         */
      //  channel.basicQos(0, 1, false);
        channel.basicQos(1); // 和上方一样的效果

        // 创建 RabbitMQ 消息监听类， 监听 RPC 连接通道
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, UTF_8);
                System.out.println("msg = " + msg);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    /**
                     *  @param deliveryTag 该消息的index
                     * @param multiple 是否批量.true:将一次性ack所有小于deliveryTag的消息。
                     */
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        // 开启一个消费者，监听队列，收到消息触发 handleDelivery 方法（回调）
        Boolean autoAck = false; // 关闭手动应答
        channel.basicConsume(HELLOWORDQUEUENAME, autoAck, consumer);

    }


}
