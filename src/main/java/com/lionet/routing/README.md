路由模式：发送消息到交换机并且要指定路由key ，消费者将队列绑定到交换机时需要指定路由key


 direct  (处理路由key)
        路由模式， 根据 不同队列绑定的不同路由key, 交换机根据不同的路由key，进行转发到不同的队列中
     
 1. 一个交换机根据一个 routing_key 发送消息， 不同队列绑定了 同一个 routing_key 都会接收到消息
 2. 消费者绑定的交换机如果在 RabbitMQ 服务器中未进行声明会报错，强烈建议在消费者也进行声明交换机防止报错
 3. 将消息发送到交换机中， 主要需要设置此消息输入那个路由key, 交换机会根据路由key进行分发消息
 4. 坑： 自己给自己写了一个坑， 同一个队列可以有多个路由key，手残一个队列绑定了两个路由key期间只会有一个消费者拿到消息
    想法：一个交换机向一个路由key发送消息， 但多个队列绑定了同一个路由key,是否可以实现？ 
            可以实现，因为交换机是根据 路由key进行转发到队列消息
 5. 同一个连接生产者是否可以将一条消息是否可以通过多个路由key，转发到不同的队列中吗？ 是的， 可以