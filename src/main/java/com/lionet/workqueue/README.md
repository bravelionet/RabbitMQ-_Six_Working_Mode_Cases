work模式：一个生产者，多个消费者，每个消费者获取到的消息唯一。

work queues
    1. 轮询分发 rund-robit
    2. 公平分发 fair， 注意公平分发必须开启手动应答
    3. 在轮询分发中个人实验与helloword模式中启动单个生产者和多个消费者没有区别