官网介绍：https://www.rabbitmq.com/getstarted.html

这里简单介绍下六种工作模式的主要特点：

简单模式：一个生产者，一个消费者

work模式：一个生产者，多个消费者，每个消费者获取到的消息唯一。

订阅模式：一个生产者发送的消息会被多个消费者获取。

路由模式：发送消息到交换机并且要指定路由    key ，消费者将队列绑定到交换机时需要指定路由key

topic模式：将路由键和某模式进行匹配，此时队列需要绑定在一个模式上，“#”匹配一个词或多个词，“*”只匹配一个词 