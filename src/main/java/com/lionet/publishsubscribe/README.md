订阅模式：一个生产者发送的消息会被多个消费者获取。

1. 一个交换机多个消费者
2. 每个队列都有监听自己队列
3. 生产者发送消息只需要将消息发送到交换机即可，交换机会转发到绑定到此交换机的队列中
4. 生产者发送消息不是直接发送到队列，而是发送到交换机后，再由交换机 转发到队列中
5. 每个消费者只能监听自己的队列
6. 交换机没有存储能力，只有转发能力，注意交换机的持久化不是消息的持久化而是交换机在RabbitMQ服务器重启后交换机是否还会存在