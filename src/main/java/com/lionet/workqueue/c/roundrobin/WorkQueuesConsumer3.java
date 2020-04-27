package com.lionet.workqueue.c.roundrobin;

import com.lionet.utlis.ConnectionFactoryUtlis;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Authror Lionet
 * @Date 2020/4/23 16:52
 * @Description 新的 API 简单队列， 消费者
 * <p>
 * 1. 获取连接
 * 2. 创建通道
 * 3. 创建监听类，将通道放入监听类中，重写回调  handleDelivery 方法
 * 3. 声明 消费者
 * 4. 启动两个消费者称 Work Queues
 */

public class WorkQueuesConsumer3 {

    private static final String HELLOWORDQUEUENAME = "Work_Queue_Test";


    private static final String UTF_8 = "utf-8";


    public static void main(String[] args) throws Exception {


        // 获取连接
        Connection connection = ConnectionFactoryUtlis.getConnection();

        // 创建连接通道
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

        // 声明队列

        channel.queueDeclare(HELLOWORDQUEUENAME, false, false, false, null);


        // 创建 RabbitMQ 消息监听类， 监听 RPC 连接通道
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             *
             * @param consumerTag 标记消费者在队列中的标签
             * @param envelope  消息的封装体
             * @param properties 消息内容头数据
             * @param body  接受的的消息体
             * @throws IOException
             */
            // 回调函数
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
             //   String tag = consumerTag.toString();
                // tag = amq.ctag-_gLJNzzVzXBAK1JtHJnIyw, 此标签将在 管控台 Queue Consumer内可以查看
              //  System.out.println("tag = " + tag);

             //   String enve = envelope.toString();
                //  简单队列中，因为无交换机 路由key 就是队列名称
                // enve = Envelope(deliveryTag=1, redeliver=false, exchange=, routingKey=hello_word_queue)
            //    System.out.println("enve " + enve);

              //  String propertie = properties.toString();
                //propertie = #contentHeader<basic>(content-type=null, content-encoding=null, headers=null, delivery-mode=null, priority=null, correlation-id=null, reply-to=null, expiration=null, message-id=null, timestamp=null, type=null, user-id=null, app-id=null, cluster-id=null)
              //  System.out.println("propertie = " + propertie);

                String msg = new String(body, UTF_8);
                System.out.println("msg = " + msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };


        /**
         * @param queue  队列的名字
         *
         * @param autoAck 是否自动应答， 如果设置会自动应答后生产者发送给消费者后将在自己的存在中删除，
         *                如果设置会手动应答将在消费者回答生产者已经消费完消息了，生产者才会才会在内存中删除消息
         *                自动应答 ： true
         *                手动应答 ： false
         *  @param Consumer   收到消息触发 handleDelivery 方法（回调）
         */

        // 开启一个消费者，监听队列，收到消息触发 handleDelivery 方法（回调）
       channel.basicConsume(HELLOWORDQUEUENAME, true, consumer);

       // channel.basicConsume(HELLOWORDQUEUENAME, true, "", true, true, null, null, consumer, null);

    }


}
